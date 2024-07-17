import * as vscode from 'vscode';
import * as fs from 'fs';
import * as os from 'node:os';
import { getQueryType } from '../query/QueryType';
import * as Path from 'node:path';

export async function config(context: vscode.ExtensionContext){
    let platform = os.type();
    await vscode.window.showInputBox({
        placeHolder: '服务器IP',
        ignoreFocusOut: true
    }).then((value)=>{
        if(value === "" || value === undefined)
        {
            vscode.window.showErrorMessage("请输入服务器地址");
            return;
        }
        else
        {
            if(hostCheck(value))
            {
                context.globalState.update("host", value);
            }
            else
            {
                vscode.window.showErrorMessage("请输入正确的服务器地址");
                return;
            }
            
        }
    });
    await vscode.window.showInputBox({
        placeHolder: '服务器端口',
        ignoreFocusOut: true
    }).then((value)=>{
        if(value === "" || value === undefined)
        {
            vscode.window.showErrorMessage("请输入服务器端口");
            return;
        }
        else
        {
            if(portCheck(value))
            {
                context.globalState.update("port", value);
            }
            else
            {
                vscode.window.showErrorMessage("请输入正确的服务器端口");
                return;
            }
            
        }
    });
    await vscode.window.showInputBox({
        placeHolder: '检测工具路径',
        ignoreFocusOut: true
    }).then((value)=>{
        if(value === "" || value === undefined)
        {
            vscode.window.showErrorMessage("请输入检测工具路径");
            return;
        }
        else
        {
            if(platform.includes("Win"))
            {
                value = value.replaceAll("\\", "/");
                value = value.replaceAll("/", "\\");
            }
            else
            {
                value = value.replaceAll("\\", "/");
            }
            context.globalState.update("classpath", value);
            let classPath = <string>context.globalState.get("classpath");
            ConfigChange(Path.join(classPath, "c2graph", "config.json"), context, "c");
            ConfigChange(Path.join(classPath, "java2graph", "config.json"), context, "java");
            ConfigChange(Path.join(classPath, "Query", "config.json"), context, "query");
            getQueryType(context, "cxx");
            getQueryType(context, "java");
            vscode.window.showInformationMessage("插件初始化完成");
        }
    });
}

function ConfigChange(filePath: string, context: vscode.ExtensionContext, type: string)
{
    if(fs.existsSync(filePath))
    {
        if(type !== "query")
        {
            let content = JSON.parse(fs.readFileSync(filePath, "utf-8"));
            content.host = context.globalState.get("host");
            content.port = Number(context.globalState.get("port"));
            if(type === "cxx")
            {
                content.includePath = [];
                content.project = "./input/";
                content.dir = "";
                content.highPrecision = false;
            }
            fs.writeFileSync(filePath, JSON.stringify(content));
        }
        else
        {
            let content = JSON.parse(fs.readFileSync(filePath, "utf-8"));
            content.gremlin.host = context.globalState.get("host");
            content.gremlin.port = Number(context.globalState.get("port"));
            fs.writeFileSync(filePath, JSON.stringify(content));
        }
    }
    else
    {
        let host: string = String(context.globalState.get("host"));
        let port = Number(context.globalState.get("port"));
        if(type === "c")
        {
            fs.writeFileSync(filePath, CConfig(host, port));
        }else{
            if(type === "java")
            {
                fs.writeFileSync(filePath, JavaConfig(host, port));
            }
            else if(type === "query")
            {
                fs.writeFileSync(filePath, QueryConfig(host, port));
            }
            else
            {
                console.log("error");
            }
        }
    }
}

function CConfig(host: string, port: number)
{
    return JSON.stringify({
            host,
            port,
            "includePath": [],
            "project": "./input/",
            "dir": "",
            "highPrecision": false
    });
}

function JavaConfig(host: string, port: number)
{
    return JSON.stringify({
        host,
        port
    });
}

function QueryConfig(host: string, port: number)
{
    return JSON.stringify({
        "gremlin": {
            host,
            port
        },
        "cache": {
            "type": "none"
        }
    });
}

function hostCheck(host: string): boolean{
    let hosts = host.split(".");
    if(hosts.length !== 4)
    {
        return false;
    }
    let i = 0;
    for(i = 0;i< hosts.length;i++)
    {
        let int = Number(hosts[i]);
        if(int >= 255 || int <= 0)
        {
            return false;
        }
    }
    return true;
}

function portCheck(port: string): boolean{
    let p = Number(port);
    if(p < 0 || p > 65535)
    {
        return false;
    }
    else
    {
        return true;
    }
}
