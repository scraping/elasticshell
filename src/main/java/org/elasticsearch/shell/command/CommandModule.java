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
package org.elasticsearch.shell.command;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.TypeLiteral;
import org.elasticsearch.common.inject.multibindings.Multibinder;
import org.elasticsearch.shell.client.RhinoClientNativeJavaObject;
import org.mozilla.javascript.NativeObject;

/**
 * Guice module that binds all the needed objects to provide and register commands to the shell
 *
 * @author Luca Cavanna
 */
public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Command> multiBinder = Multibinder.newSetBinder(binder(), Command.class);
        multiBinder.addBinding().to(ExitCommand.class).asEagerSingleton();
        multiBinder.addBinding().to(HelpCommand.class).asEagerSingleton();
        multiBinder.addBinding().to(PrintCommand.class).asEagerSingleton();
        multiBinder.addBinding().to(VersionCommand.class).asEagerSingleton();
        multiBinder.addBinding().to(LoadCommand.class).asEagerSingleton();

        //Rhino specific commands
        multiBinder.addBinding().to(new TypeLiteral<ParseJsonCommand<Object>>() {}).asEagerSingleton();
        multiBinder.addBinding().to(new TypeLiteral<TransportClientCommand<RhinoClientNativeJavaObject>>(){}).asEagerSingleton();
        multiBinder.addBinding().to(new TypeLiteral<NodeClientCommand<RhinoClientNativeJavaObject>>(){}).asEagerSingleton();
        multiBinder.addBinding().to(new TypeLiteral<LocalNodeCommand<RhinoClientNativeJavaObject, NativeObject, Object>>(){}).asEagerSingleton();

        bind(RhinoCommandRegistrar.class).asEagerSingleton();
    }
}