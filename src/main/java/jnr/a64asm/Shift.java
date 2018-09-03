/*
 * Copyright (C) 2018 Ossdev07
 *
 * This file is part of the JNR project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jnr.a64asm;

public final class Shift extends Operand {
    private final int value;
    private final int type; /* type will be of SHIFT.java */

    public Shift(int type, int value) {
        super(OP.OP_SHIFT, 0);
        this.value = value;
        this.type = type;
    }

    public long value() {
        return value;
    }

    public long type() {
        return type;
    }

}
