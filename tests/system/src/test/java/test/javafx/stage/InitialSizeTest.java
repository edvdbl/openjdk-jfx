/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package test.javafx.stage;

import com.sun.javafx.PlatformUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.Util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public class InitialSizeTest {
    static CountDownLatch startupLatch;
    static Stage stage;

    private static final double INIT_SIZE = 100.d;

    public static void main(String[] args) throws Exception {
        initFX();

        try {
            InitialSizeTest test = new InitialSizeTest();
            test.testInitialSize();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            teardown();
        }
    }

    public static class TestApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            Platform.runLater(startupLatch::countDown);
            primaryStage.setScene(new Scene(new Group()));
            stage = primaryStage;
            stage.setWidth(INIT_SIZE);
            stage.setHeight(INIT_SIZE);
            stage.show();
        }
    }

    @BeforeClass
    public static void initFX() {
        if (startupLatch == null) {
            startupLatch = new CountDownLatch(1);

            new Thread(() -> Application.launch(TestApp.class, (String[]) null)).start();
            try {
                if (!startupLatch.await(15, TimeUnit.SECONDS)) {
                    fail("Timeout waiting for FX runtime to start");
                }
            } catch (InterruptedException ex) {
                fail("Unexpected exception: " + ex);
            }
        }
    }

    @Test
    public void testInitialSize() throws Exception {
        Util.sleep(200);
        Assert.assertTrue(stage.isShowing());
        Assert.assertEquals("Stage height", INIT_SIZE, stage.getHeight(), .1d);
        Assert.assertEquals("Stage width", INIT_SIZE, stage.getWidth(), .1d);
    }

    @AfterClass
    public static void teardown() {
        Platform.runLater(stage::hide);
        Platform.exit();
    }
}
