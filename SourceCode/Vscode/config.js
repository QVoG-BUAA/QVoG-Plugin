"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.config = config;
const vscode = __importStar(require("vscode"));
const fs = __importStar(require("fs"));
async function config(context) {
    await vscode.window.showInputBox({
        placeHolder: '服务器IP',
        ignoreFocusOut: true
    }).then((value) => {
        if (value === "") {
            vscode.window.showErrorMessage("请输入服务器地址");
            return;
        }
        else {
            context.globalState.update("host", value);
        }
    });
    await vscode.window.showInputBox({
        placeHolder: '服务器端口',
        ignoreFocusOut: true
    }).then((value) => {
        if (value === "") {
            vscode.window.showErrorMessage("请输入服务器端口");
            return;
        }
        else {
            context.globalState.update("port", value);
        }
    });
    await vscode.window.showInputBox({
        placeHolder: '检测工具路径',
        ignoreFocusOut: true
    }).then((value) => {
        if (value === "") {
            vscode.window.showErrorMessage("请输入检测工具路径");
            return;
        }
        else {
            value?.replaceAll("\\", "/");
            context.globalState.update("classpath", value);
        }
    });
    // console.log(context.globalState.get("host"));
    // console.log(context.globalState.get("port"));
    // console.log(context.globalState.get("classpath"));
    changeConfigFile("./config.json", context);
}
function changeConfigFile(filePath, context) {
    if (fs.existsSync(filePath)) {
        let content = JSON.parse(fs.readFileSync(filePath, "utf-8"));
        content.host = context.globalState.get("host");
        content.port = context.globalState.get("port");
        fs.writeFileSync(filePath, content);
        return true;
    }
    else {
        return false;
    }
}
//# sourceMappingURL=config.js.map