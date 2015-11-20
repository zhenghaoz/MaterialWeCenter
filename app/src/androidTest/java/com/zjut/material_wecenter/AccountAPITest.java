package com.zjut.material_wecenter;

import android.test.AndroidTestCase;

import com.zjut.material_wecenter.models.LoginProcess;

/**
 * Copyright (C) 2015 Jinghong Union of ZJUT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class AccountAPITest extends AndroidTestCase {

    public void testLogin() {
        LoginProcess process = Client.LoginProcess("201426811427", "zh10086");
        assertNotNull(process);
        assertEquals(process.getErrno(), 1);
        assertEquals(process.getErr(), null);
        assertEquals(process.getRsm().getUser_name(), "LarryNyive");
        assertEquals(process.getRsm().getUid(), 201426811427L);
        assertEquals(process.getRsm().getAvatar_file(), "2014/2681/14/27_avatar_max.jpg");
    }
}
