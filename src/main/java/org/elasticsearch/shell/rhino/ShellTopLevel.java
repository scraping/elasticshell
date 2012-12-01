/*
 * Licensed to Luca Cavanna (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.shell.rhino;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.inject.name.Named;
import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.ToolErrorReporter;

import java.io.PrintStream;

@Singleton
public class ShellTopLevel extends ImporterTopLevel {

    private final PrintStream out;

    @Inject
    ShellTopLevel(@Named("shellOutput") PrintStream out){

        this.out = out;

        //TODO default imports (check mvel)

        Context context = Context.enter();
        try {
            initStandardObjects(context, false);
        } finally {
            Context.exit();
        }


        //defineProperty("h", new Test(), ScriptableObject.DONTENUM);

        //define function and register commands without prefix

        defineFunctionProperties(new String[]{"help"}, getClass(), ScriptableObject.DONTENUM);


        /*Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("help")) {
                FunctionObject f = new FunctionObject("help", method, this);
                defineProperty("help", f, ScriptableObject.DONTENUM);
            }
        }*/

    }

/*    public void defineFunctionProperties(String[] names, Class<?> clazz,
                                         int attributes)
    {
        Method[] methods = FunctionObject.getMethodList(clazz);
        for (int i=0; i < names.length; i++) {
            String name = names[i];
            Method m = FunctionObject.findSingleMethod(methods, name);
            if (m == null) {
                throw Context.reportRuntimeError2(
                        "msg.method.not.found", name, clazz.getName());
            }
            FunctionObject f = new FunctionObject(name, m, this);
            defineProperty(name, f, attributes);
        }
    }*/


    public static void help(Context cx, Scriptable thisObj,
                            Object[] args, Function funObj) {

        ShellTopLevel shellTopLevel = getInstance(funObj);
        shellTopLevel.out.println("help me!"/*shellTopLevel.helpCommand.execute()*/);
    }

    private static ShellTopLevel getInstance(Function function)
    {
        Scriptable scope = function.getParentScope();
        if (!(scope instanceof ShellTopLevel))
            throw reportRuntimeError("msg.bad.shell.function.scope",
                    String.valueOf(scope));
        return (ShellTopLevel)scope;
    }

    static RuntimeException reportRuntimeError(String msgId, String msgArg)
    {
        String message = ToolErrorReporter.getMessage(msgId, msgArg);
        return Context.reportRuntimeError(message);
    }
}
