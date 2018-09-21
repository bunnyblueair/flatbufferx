-keepattributes Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*
# LoganSquareX
#nothing need keep
#-keep class com.bluelinelabs.logansquare.** { *; }
-keep @io.logansquarex.core.annotation.JsonObject class *
#-keep class **$$JsonObjectMapper { *; }

# Jackson
-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}




#Keep Gson
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-dontwarn com.fasterxml.jackson.databind.**
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry
-dontnote  java.beans.**
-dontnote java.nio.file.**
-dontwarn okio.**
-dontwarn java.beans.**
-dontnote  sun.misc.**


#keep for demo
-keepclassmembers class io.logansquarex.demo.widget.BarChart{
*;
}

-keepclassmembers class io.logansquarex.demo.model.User{
*;
}
-keepclassmembers class io.logansquarex.demo.model.Response{
*;
}
-keepclassmembers class io.logansquarex.demo.model.Name{
*;
}
-keepclassmembers class io.logansquarex.demo.model.Image{
*;
}
-keepclassmembers class io.logansquarex.demo.model.Friend{
*;
}