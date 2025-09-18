package com.github.yasevich.menugen.data.openai

import android.content.Context
import android.net.Uri
import com.github.yasevich.menugen.data.UploadRepository
import com.github.yasevich.menugen.data.openai.model.OpenAiShopMenu
import com.github.yasevich.menugen.data.openai.model.asShopMenu
import com.github.yasevich.menugen.di.IoDispatcher
import com.github.yasevich.menugen.model.RestaurantMenu
import com.openai.client.OpenAIClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams
import com.openai.models.responses.ResponseInputImage
import com.openai.models.responses.ResponseInputItem
import com.openai.models.responses.ResponseTextConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.io.encoding.Base64

class OpenAiUploadRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val client: OpenAIClient,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UploadRepository {

    private val coroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    private var jobs = mutableMapOf<Uri, Deferred<Result<RestaurantMenu>>>()

    override suspend fun uploadMenu(uri: Uri): Result<RestaurantMenu> {
        return jobs.getOrPut(uri) {
            coroutineScope.async {
                uploadMenuSuspending(uri)
            }
        }.await()
    }

    private suspend fun uploadMenuSuspending(uri: Uri): Result<RestaurantMenu> {
        return withFile(uri) { imageUrl ->
            val inputImage = ResponseInputImage.builder()
                .detail(ResponseInputImage.Detail.AUTO)
                .imageUrl(imageUrl)
                .build()

            val message = ResponseInputItem.Message.builder()
                .role(ResponseInputItem.Message.Role.USER)
                .addInputTextContent("This is a restaurant menu. Identify all menu groups and items. If there are notes like “Add chicken +9”, include them into item description.")
                .addContent(inputImage)
                .build()

            val text = ResponseTextConfig.builder()
                .format(OpenAiShopMenu::class.java)
                .build()

            val params = ResponseCreateParams.builder()
                .inputOfResponse(response = listOf(ResponseInputItem.ofMessage(message)))
                .model(ChatModel.GPT_4_1_MINI)
                .text(text)
                .build()

            client.responses()
                .create(params)
                .output()
                .asSequence()
                .map { output -> output.asMessage() }
                .flatMap { message -> message.content() }
                .map { content -> content.asOutputText() }
                .map { menu -> menu.asShopMenu() }
                .first()
        }
    }

    private suspend fun <T> withFile(uri: Uri, block: (imageUrl: String) -> T): Result<T> {
        return execute {
            val contentResolver = context.contentResolver

            val mimeType = checkNotNull(contentResolver.getType(uri)) {
                "Unable to resolve MIME type for $uri"
            }

            val bytes = checkNotNull(contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }) {
                "Unable to decode the image: $uri"
            }

            val base64Image = Base64.encode(bytes)

            block("data:$mimeType;base64,$base64Image")
        }
    }

    private suspend fun <T> execute(block: () -> T): Result<T> {
        return withContext(dispatcher) {
            runCatching(block)
        }
    }
}
