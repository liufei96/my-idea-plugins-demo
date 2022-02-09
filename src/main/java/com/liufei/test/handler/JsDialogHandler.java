package com.liufei.test.handler;

import com.liufei.test.util.dialog.AlertDialog;
import com.liufei.test.util.dialog.ConfirmDialog;
import com.liufei.test.util.dialog.PromptDialog;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefJSDialogCallback;
import org.cef.handler.CefJSDialogHandler;
import org.cef.misc.BoolRef;

import java.awt.*;

import static org.cef.handler.CefJSDialogHandler.JSDialogType.*;

public class JsDialogHandler implements CefJSDialogHandler {

    @Override
    public boolean onJSDialog(CefBrowser browser,
                              String origin_url,
                              JSDialogType dialog_type,
                              String message_text,
                              String default_prompt_text,
                              CefJSDialogCallback callback,
                              BoolRef suppress_message) {
        // 不抑制消息
        suppress_message.set(false);
        if (dialog_type == JSDIALOGTYPE_ALERT) {
            // alert 对话框
            EventQueue.invokeLater(() -> {
                new AlertDialog("注意", message_text).show();
                callback.Continue(true, "");
            });
            return true;
        }
        if (dialog_type == JSDIALOGTYPE_CONFIRM) {
            // confirm 对话框
            EventQueue.invokeLater(() -> {
                 boolean isOk = new ConfirmDialog("注意", message_text).showAndGet();
                 callback.Continue(isOk, "");
            });
            return true;
        }

        if (dialog_type == JSDIALOGTYPE_PROMPT) {
            // prompt 对话框
            EventQueue.invokeLater(() -> {
                PromptDialog promptDialog = new PromptDialog("执行回访", message_text);
                boolean isOk = promptDialog.showAndGet();
                String text = promptDialog.getText();
                callback.Continue(isOk, text);
            });
            return true;
        }
        // 返回true，表明自行处理
        return false;
    }

    @Override
    public boolean onBeforeUnloadDialog(CefBrowser cefBrowser, String s, boolean b, CefJSDialogCallback cefJSDialogCallback) {
        return false;
    }

    @Override
    public void onResetDialogState(CefBrowser cefBrowser) {

    }

    @Override
    public void onDialogClosed(CefBrowser cefBrowser) {

    }
}
