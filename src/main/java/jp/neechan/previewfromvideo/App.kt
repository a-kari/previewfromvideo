package jp.neechan.previewfromvideo

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import java.io.File
import javax.imageio.ImageIO

object App {

    private const val PREVIEW_FORMAT    = "jpg"
    private const val PREVIEW_DIRECTORY = "/home/akari/previewfromvideo/previews/"
    private val       converter         = Java2DFrameConverter()

    @JvmStatic
    fun main(args: Array<String>) {
        val isSuccess = savePreviewFromVideo("/home/akari/previewfromvideo/bunny.mp4")
        println(if (isSuccess) "Success" else "Failure")
    }

    private fun savePreviewFromVideo(videoPath: String): Boolean {
        var grabber: FFmpegFrameGrabber? = null
        try {
            val video       = File(videoPath)
            val previewPath = PREVIEW_DIRECTORY + video.nameWithoutExtension + "." + PREVIEW_FORMAT
            grabber         = FFmpegFrameGrabber(video)

            grabber.start()
            return ImageIO.write(converter.convert(grabber.grabKeyFrame()), PREVIEW_FORMAT, File(previewPath))

        } catch (e: Exception) {
            e.printStackTrace()

        } finally {
            grabber?.stop()
        }

        return false
    }
}