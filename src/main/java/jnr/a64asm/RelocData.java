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

/**
 * Reloc to absolute address data
 */
class RelocData {

    static enum Type {
        ABSOLUTE_TO_ABSOLUTE,
        RELATIVE_TO_ABSOLUTE,
        ABSOLUTE_TO_RELATIVE,
        ABSOLUTE_TO_RELATIVE_TRAMPOLINE;
    };

    /** Type of relocation. */
    final Type type;

    /** Size of relocation (4 or 8 bytes). */
    final int size;

    /** Offset from code begin address (in the emitted code data stream). */
    final int offset;

    /** Relative displacement or absolute address. */
    final long destination;

    public RelocData(Type type, int size, int offset, long destination) {
        this.type = type;
        this.size = size;
        this.offset = offset;
        this.destination = destination;
    }
}
