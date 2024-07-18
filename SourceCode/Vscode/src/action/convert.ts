import * as vscode from 'vscode';
import {exec} from 'child_process';
import * as os from 'os';
import { Context } from 'mocha';
import { stdin, stdout } from 'process';
import { out } from '../extension';
import * as Path from 'node:path';

export async function convert(context: vscode.ExtensionContext) {
    const res = await vscode.window.withProgress({
        location: vscode.ProgressLocation.Notification,
        title: "正在将代码转化为图，请稍候···",
        cancellable: true
    }, async (progress, token) => {
        let classPath = String(context.globalState.get("classpath"));
        let editor = vscode.window.activeTextEditor;
        if (editor === undefined) {
            vscode.window.showErrorMessage("the editor is not active");
            return;
        }
        let filePath = editor.document.fileName;
        let platform = os.type();
        let filename = "";
        if(platform.includes("Win"))
        {   
            filename = filePath.split("\\")[filePath.split("\\").length - 1];
        }
        else
        {
            filename = filePath.split("/")[filePath.split("/").length - 1];
        }

        if(filename?.endsWith("c") || filename?.endsWith(".cpp"))
        {
            let cpath = Path.join(classPath, "c2graph");
            await move(Path.join(cpath, "input"), filePath, platform);
            let cmd = "java -jar C2Graph.jar";
            exec(cmd, {
                cwd: cpath
            }, (error, stdout, stderr)=>{
                if(error)
                {
                    console.error(`exec error: ${error}`);
                    del(Path.join(cpath, "input", filename), platform);
                    return;
                }
                if(stdout)
                {
                    console.log(`${stdout}`);
                    stdout = stdout.replaceAll("[0m", "");
                    stdout = stdout.replaceAll("[31m", "");
                    stdout = stdout.replaceAll("[36m", "");
                    vscode.window.showInformationMessage("转换完成");
                    out.clear();
                    out.append(stdout);
                    out.show();
                    del(Path.join(cpath, "input", filename), platform);
                    return;
                }
                if(stderr)
                {
                    console.log(`${stderr}`);
                    out.clear();
                    out.append(stderr);
                    out.show();
                    del(Path.join(cpath, "input", filename), platform);
                    return;
                }
                // vscode.window.showInformationMessage(stdout);
                
            });
        }
        else if(filename?.endsWith("java"))
        {
            let javapath = Path.join(classPath, "java2graph");
            await move(Path.join(javapath, "input"), filePath, platform);
            let cmd = "java -jar Java2Graph.jar -i input -o output";
            exec(cmd, {
                cwd: javapath
            }, (error, stdout, stderr)=>{
                if(error)
                {
                    console.error(`exec error: ${error}`);
                    del(Path.join(javapath, "input", filename), platform);
                    return;
                }
                if(stdout)
                {
                    console.log(`${stdout}`);
                    out.clear();
                    out.append(stdout);
                    out.show();
                    del(Path.join(javapath, "input", filename), platform);
                    return;
                }
                if(stderr)
                {
                    console.log(`${stderr}`);
                    out.clear();
                    out.append(stderr);
                    out.show();
                    del(Path.join(javapath, "input", filename), platform);
                    return;
                }
                
            });
        }
        else
        {
            vscode.window.showErrorMessage("目前仅支持C与Java两种语言，目标语言不支持");
        }
    });
}

export async function move(targetPath: string, filePath: string, platform: string)
{
    let cmd = "";
    if(platform.startsWith("Win"))
    {
        cmd = "copy "+ filePath + " " + targetPath;
    }
    else
    {
        cmd = "cp " + filePath + " " + targetPath;
    }
    exec(cmd, (error, stdout, stderr)=>{
        if(error)
        {
            console.log(error);
            return;
        }
    });
}

export async function del(targetFilePath: string, platform: string)
{
    let cmd = "";
    if(platform.startsWith("Win"))
    {
        cmd = "del "+ targetFilePath;
    }
    else
    {
        cmd = "rm " + targetFilePath;
    }
    exec(cmd, (error, stdout, stderr)=>{
        if(error)
        {
            console.log(error);
            return;
        }
    });
}