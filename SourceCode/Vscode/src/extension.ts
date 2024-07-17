// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
import * as vscode from 'vscode';
import {exec} from 'child_process';
import { config } from './action/config';
import { stderr, stdin, stdout } from 'process';
import { convert } from './action/convert';
import { text } from 'stream/consumers';
import { getQueryType } from './query/QueryType';
import { query } from './action/query';

export const out = vscode.window.createOutputChannel("QVoG");
// This method is called when your extension is activated
// Your extension is activated the very first time the command is executed
export function activate(context: vscode.ExtensionContext) {

	// Use the console to output diagnostic information (console.log) and errors (console.error)
	// This line of code will only be executed once when your extension is activated
	console.log('Congratulations, your extension "qvog-plugin" is now active!');
	// The command has been defined in the package.json file
	// Now provide the implementation of the command with registerCommand
	// The commandId parameter must match the command field in package.json
	const disposable = vscode.commands.registerCommand('qvog-plugin.helloWorld', () => {
		// The code you place here will be executed every time your command is executed
		// Display a message box to the user
		vscode.window.showInformationMessage('Hello World from QVog-Plugin!');
		getQueryType(context, "java");
	});

	context.subscriptions.push(disposable);

	context.subscriptions.push(vscode.commands.registerCommand('qvog-plugin.config', ()=>{
		config(context);
	}));

	context.subscriptions.push(vscode.commands.registerCommand('qvog-plugin.convert', ()=>{
		convert(context);
	}));

	context.subscriptions.push(vscode.commands.registerCommand('qvog-plugin.query', ()=>{
		query(context);
	}));
}

// This method is called when your extension is deactivated
export function deactivate() {}
