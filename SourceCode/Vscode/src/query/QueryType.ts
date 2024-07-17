import * as vscode from 'vscode';
import {exec} from 'child_process';
import { error } from 'console';
import * as Path from 'node:path';
import { stderr, stdin, stdout } from 'process';
import * as os from 'node:os';

export async function getQueryType(context: vscode.ExtensionContext, language: string) {
    let classPath = String(context.globalState.get("classpath"));
    let QueryPath = Path.join(classPath, "Query");
    let cmd = "java -jar QVoGine.jar --language " + language + " --list";
    let eol = "";
    let platform = os.type();
    if(platform.includes("Win"))
    {
        eol = "\r\n";
    }
    else
    {
        eol = "\n";
    }
    exec(cmd, {
        cwd: QueryPath
    }, (error, stdout, stdin)=>{
        if(error)
        {
            console.error(error);
        }
        if(stdout)
        {
            console.log(stdout);
            let queries = stdout.split(eol);
            let result =  queries.slice(1, queries.length - 1);
            result.push("all");
            context.globalState.update(language + "QueryType", result);
        }
        if(stderr)
        {
            console.log(stderr);
        }
    });
}