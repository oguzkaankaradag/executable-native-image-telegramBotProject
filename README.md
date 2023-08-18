# executable-native-image-telegramBotProject
Command line app using GraalVM . Application checks given urls status code 2xx else send message to configured telegram user or telegram group


# Before clone the app ,
Development environment must be configured with GraalVM and the Native Image tool required for native-image.
We need to check enviroment configuration , run the command below ;

`java -version`  GraalVM should be shown us as a Runtime Enviroment at the console output.

Also run 
`native-image --version` This also gives you native image version . 

# If above commands work properly ,
and outputs are as expected . Your enviroment configuration is ready.You can skip here .

# If your output is different ,
console gives you "command not found" or different Runtime Enviroment from GraalVM , we need 
to install GraalVM and native-image or maybe it is necessary to configure enviroment varible . 

# Installation and Configuration for Enviroment Variable
Just follow through the https://www.graalvm.org/latest/docs/getting-started/ to get necessary tools and configurations before 
starting the generate executable native-image .

# When Installation and Configuration is satisfied ,
You can run these commands;
`java -version` to check Graalvm is Runtime Enviroment.
`native-image --version` to check native-image tool also installed without problem .

# After necessary configuration is prepared we can start building native-image
Clone source code in desired working directory 
`git clone https://github.com/oguzkaankaradag/executable-native-image-telegramBotProject.git`
You can open project with your IDE or open your terminal/command prompt change directory to the project root. 

Several profiles can be used to build native-image.In the pom.xml file you can see "native" and "java_agen" profiles. 
`native` : This profile builds an executable file using GraalVM Native Image.
`java_agent` : This profile builds the Java application with a tracing agent. More on this later. 

Open terminal at the project root directory or directly from IDE terminal , run the below command ;
`mvn clean package exec:exec` These commands ; 

1-Creates a runnable JAR file containing the application. This JAR file will be later used by Native Image.

2-Runs the application by running the exec plugin





# --------------------------------------------------------------
#  Turn a Java Application into an Executable File Using One of The Options Below
# --------------------------------------------------------------

1 - Without Using Maven Profile

 - To begin, check that you have a compiled JAR file in your target directory. 
You should see  file  with "SNAPSHOT-jar-with-dependencies.jar" extension. 

 - Generate an executable file from the command line. You do not need to use the Maven plugin to use GraalVM Native Image,
but it can help. Run the following from the root directory of the project;

`native-image -jar ./target/ExecutableNativeImageTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar --no-fallback -H:Class=org.example.App -H:Name=telegramBot`
This will generate an executable file called "telegramBot" within the current directory.

- Run this executable file as follows:
 `./telegramBot`

 2 - With Maven Profile 
 
 The project "pom.xml" file contains the  snippet that demonstrates how to build an executable file using the plugin.
  - To build the executable file using the Maven profile, run;
 `mvn clean package -Pnative`
 The Maven build places the executable file, "telegramBot", into the target directory.
 You can run the executable file as follows `./target/telegramBot`

 3 - Using the Tracing Agent
 
 - Run the application with the tracing agent:
 `java -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image -cp ./target/ExecutableNativeImageTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar org.example.App`
the check the configuration files that the tracing agent created for us ;
`ls -l src/main/resources/META-INF/native-image/`
There will be ;

jni-config.json
native-image.properties
predefined-classes-config.json
proxy-config.json
reflect-config.json
resource-config.json
serialization-config.json

configuration files must be created to handle dynamic features of Java . 

The project contains a Maven profile that can do this for you. Run the following command to use the tracing agent,
`mvn clean package exec:exec -Pjava_agent`
Now re-build the executable file again. This time the configuration files produced by the tracing agent will be applied,
`mvn package -Pnative`
Execute the generated file,
`./target/telegramBot`


 



