import * as vscode from 'vscode';
import {exec} from 'child_process';
import * as os from 'os';
import { Context } from 'mocha';
import { stdin, stdout } from 'process';
import { getQueryType } from '../query/QueryType';
import { out } from '../extension';
import * as Path from "node:path";

export async function query(context: vscode.ExtensionContext)
{
    
    const res = await vscode.window.withProgress({
        location: vscode.ProgressLocation.Notification,
        title: "正在检测中，请稍候···",
        cancellable: true
    }, async (progress, token) => {
        let classPath = String(context.globalState.get("classpath"));
        let editor = vscode.window.activeTextEditor;
        if (editor === undefined) {
            vscode.window.showErrorMessage("the editor is not active");
            return;
        }
        let filename = editor.document.fileName;
        let QueryPath = Path.join(classPath, "Query");
        if(filename.endsWith("c") || filename.endsWith("cpp"))
        {
            let CQueryType = <Array<string>>context.globalState.get("cxxQueryType");
            if(CQueryType === undefined)
            {
                vscode.window.showErrorMessage("插件未初始化，请先完成插件设置。");
                return;
            }
            vscode.window.showQuickPick(CQueryType, {
                canPickMany: true
            }).then((data)=>{
                if(data === undefined || data.length === 0)
                {
                    vscode.window.showErrorMessage("请选择查询内容");
                    return;
                }

                let cmd = "java -jar QVoGine.jar --language cxx";
                if(!data.includes("all"))
                {
                    let i = 0;
                    cmd += " --query";
                    for(i = 0;i < data.length;i++)
                    {
                        cmd += " " + data[i];
                    }
                }
                exec(cmd, {
                    cwd: QueryPath
                }, (error, stdout, stderr)=>{
                    if(error)
                    {
                        console.error(error);
                    }
                    if(stdout)
                    {
                        console.log(stdout);
                        stdout = stdout.replaceAll("[0m", "");
                        stdout = stdout.replaceAll("[31m", "");
                        stdout = stdout.replaceAll("[36m", "");
                        vscode.window.showInformationMessage("查询完成");
                        out.clear();
                        out.append(stdout);
                        out.show();
                    }
                    if(stderr)
                    {
                        console.error(stderr);
                    }
                });
            });
        }
        else if(filename.endsWith("java"))
        {
            let JavaQueryType = <Array<string>>context.globalState.get("javaQueryType");
            if(JavaQueryType === undefined)
            {
                vscode.window.showErrorMessage("插件未初始化，请先完成插件设置。");
                return;
            }
            vscode.window.showQuickPick(JavaQueryType, {
                canPickMany: true
            }).then((data)=>{
                if(data === undefined || data.length === 0)
                {
                    vscode.window.showErrorMessage("请选择查询内容");
                    return;
                }

                let cmd = "java -jar QVoGine.jar --language java";
                if(!data.includes("all"))
                {
                    let i = 0;
                    cmd += " --query";
                    for(i = 0;i < data.length;i++)
                    {
                        cmd += " " + data[i];
                    }
                }
                exec(cmd, {
                    cwd: QueryPath
                }, (error, stdout, stderr)=>{
                    if(error)
                    {
                        console.error(error);
                    }
                    if(stdout)
                    {
                        console.log(stdout);
                        stdout = stdout.replaceAll("[0m", "");
                        stdout = stdout.replaceAll("[31m", "");
                        stdout = stdout.replaceAll("[36m", "");
                        vscode.window.showInformationMessage("查询完成");
                        out.clear();
                        out.append(stdout);
                        out.show();
                    }
                    if(stderr)
                    {
                        console.error(stderr);
                    }
                });
            });
        }
        else
        {
            vscode.window.showErrorMessage("目前仅支持C与Java两种语言，目标语言不支持");
        }
    });
}

// function outProduce(out: string): Map<string, Array<[string, string]>>
// {
//     let queryResult = out.split("==========")[1];
//     let queryResults = queryResult.split("===== ");
//     let Res: Map<string, Array<[string, string]>> = new Map<string, Array<[string, string]>>();
//     let i = 0;
//     for(i = 0;i < queryResults.length;i++)
//     {
//         let query = queryResults[i];
//         if(query.includes("Executing"))
//         {

//         }
//         else
//         {

//         }
//     }
// }