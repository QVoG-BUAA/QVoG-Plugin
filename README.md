# QVoG-Plugin
这是一个为[QVoG检测工具](https://github.com/QVoG-BUAA)开发的插件，且支持Vscode、Jetbrains双平台

## 开始之前

目前，该插件需要用户自行在本地根据下方的结构设置初始的检测工具。用户可参考对应项目的ReadME文档中项目构建的部分，自行构建检测工具。

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

此外，该检测工具要求用户提供一个安装了Gremlin Server的服务器，用户可使用本仓库中提供的[安装脚本](https://github.com/QVoG-BUAA/QVoG-Plugin/tree/main/tools)进行安装，其会在执行文件夹下进行安装，用户可在安装结束后，通过Gremlin Server文件夹下bin/gremlin-server.sh启动Gremlin Server。

## 插件使用

首先，用户需要在插件的config选项中输入服务器的地址、端口和本地检测工具的路径。

随后，用户可通过右键菜单中的convert选项，将目标文件转化为图并上传至服务器中，供后续检测使用

最后，用户可在query选项中选择目标的检测种类以对上传的目标文件进行检测。
