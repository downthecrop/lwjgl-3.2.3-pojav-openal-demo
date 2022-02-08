# lwjgl-3.2.3-pojav-openal-demo

Yes It's very complicated, but I have sound.

I worked on this for many many many hours to understand just how the Pojav launcher is implementing openal. Long and short of it you are going to need to implement your audio with lwjgl libs but don't include the linux natives they are included in the library path in pojav and has been modified to work in android.

Things to note here are that when you are creating audio with lwjgl there are a couple javax implementations that you need to stay away from. For example I first followed along with this https://www.youtube.com/watch?v=r6GGRCQtReE which uses WaveData.java which can be ported to your project but loads the file which uses AudioInputStream from javax which is no good for our uses.

https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/util/WaveData.java

Anyway all of this is very much black magic to me currently even though I am able to make music play on Pojavs modinstaller environment. I'm going to bed now.
