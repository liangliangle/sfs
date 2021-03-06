/*
 * Copyright 2016 The Simple File Server Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sfs.jobs;

import io.vertx.core.MultiMap;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.sfs.Server;
import org.sfs.VertxContext;
import org.sfs.encryption.MasterKeys;
import org.sfs.rx.Defer;
import rx.Observable;

public class ReEncryptMasterKeys extends AbstractJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReEncryptMasterKeys.class);

    @Override
    public String id() {
        return Jobs.ID.RE_ENCRYPT_MASTER_KEYS;
    }

    @Override
    public Observable<Void> executeImpl(VertxContext<Server> vertxContext, MultiMap parameters) {
        return execute0(vertxContext);
    }

    @Override
    public Observable<Void> stopImpl(VertxContext<Server> vertxContext) {
        return Defer.aVoid();
    }


    protected Observable<Void> execute0(VertxContext<Server> vertxContext) {
        return Defer.aVoid()
                .flatMap(index -> {
                    MasterKeys masterKeys = vertxContext.verticle().masterKeys();
                    return masterKeys.maintain(vertxContext);
                });
    }
}

