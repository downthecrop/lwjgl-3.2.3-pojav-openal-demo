import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;

/*
 @author downthecrop
*/

public class ALCTest {

    public static void playSound(String name) throws Exception{

        // init audio device
        String defaultDevice = alcGetString(0,ALC_DEFAULT_DEVICE_SPECIFIER);
        long audioDevice = alcOpenDevice(defaultDevice);
        int[] attributes = {0};
        long audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        IntBuffer channelBuffer = stackMallocInt(1);
        IntBuffer sampleBuffer = stackMallocInt(1);

        if(!alCapabilities.OpenAL10) {
            assert false : "Audio lib not supported";
        }

        ShortBuffer rawAudio = stb_vorbis_decode_filename(name,channelBuffer,sampleBuffer);

        if (rawAudio == null){
            System.out.println("Error loading audio");
        }

        int channels = channelBuffer.get();
        int samplerate = sampleBuffer.get();

        int format = -1;
        if (channels == 1){
            format = AL_FORMAT_MONO16;
        } else if (channels == 2){
            format = AL_FORMAT_STEREO16;
        }

        int bufferId = alGenBuffers();
        alBufferData(bufferId,format,rawAudio,samplerate);
        int sourceid = alGenSources();
        alSourcei(sourceid,AL_BUFFER,bufferId);
        alSourcei(sourceid,AL_LOOPING,0);
        alSourcei(sourceid,AL_POSITION,0);
        alSourcei(sourceid,AL_GAIN,1);

        alSourcePlay(sourceid);

        // We just keep the thread alive to keep the music going
        Thread.sleep(100000);
        System.out.println("end");

        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
    }

    public static void main(String[] args) throws Exception {
        /*

         Pass in the .ogg sound to play as arg[0]
         You either need to extract the natives of
         OpenAL, LWJGL, and STB or include the bundled LWJGL
         Libs. Because we are using precompiled Android libs
         extracted them to my desktop and added

         -Djava.library.path="C:/Users/Anon/Desktop/natives/"

         VM arguments. You want something like the included example
         image. https://i.imgur.com/wveAG8N.png
         */

        playSound(args[0]);
        System.exit(0);
    }
}
