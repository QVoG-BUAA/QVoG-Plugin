# QVoG-Plugin
This is a plugin for the project [QVoG](https://github.com/QVoG-BUAA/) based on the IntelliJ Platform SDK. This is a tool for statically detecting software defects and vulnerability via code graph queries.

## Before Move On

In the current version, this plugin requires users to configure the initial version of the detection tool themselves. Specifically, users need to store the corresponding detection tools extracted in jar format locally in the specified format, or directly use the content prepared for the users。

```
-─/tools/QVoG-Executor
    ├─c2graph
    │  ├─input
    │  ├─C2Graph.jar
    |  ├─config.json
    ├─java2graph
    │  ├─input
    │  ├─output
    │  │  ├─AST
    │  │  ├─CFG
    │  │  ├─CG
    │  │  ├─newAST
    │  │  └─PDG
    |  ├─Java2Graph.jar
    |  ├─config.json
    └─Query
    |  ├─lib
    |  |  ├─Query.jar
    |  ├─QVoGine.jar
    |  ├─config.json
```

In addition, this detection tool requires you to configure the Gremlin server properly. You can use the shell script in the tools directory to install the Gremlin Server. All you need to do is to start the server and expose corresponding ports. You can view the corresponding document for detailed information

## How to use it

Firstly, please set the IP address and port number of the server, as well as configure the local detection tool directory, such as `E:/CodeGraphQLExtended/tools/CGQL`

Subsequently, you can convert the file to graphs and upload to the server.

Finally, you can query the information you want from the server.
