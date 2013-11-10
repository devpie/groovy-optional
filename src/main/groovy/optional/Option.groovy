/**
 * Copyright 2013 Jan Marco Müller <jan.marco.mueller@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package optional

import groovy.transform.TupleConstructor

/**
 * The class Option for Groovy.
 */
abstract class Option<T> implements Iterable<T> {
    static <A> Option<A> of(A o) {
        if (o) {
            new Some(o)
        } else {
            None.INSTANCE
        }
    }

    abstract <B extends T> Option<B> orElse(Closure<B> c)

    abstract <B extends T> B getOrElse(Closure<B> c)

    abstract <B> Option<B> collect(Closure<B> c)

    abstract Option<T> each(Closure c)
}

@TupleConstructor
class Some<T> extends Option<T> {
    T o

    @Override
    <B extends T> Option<T> orElse(Closure<B> c) {
        return this
    }

    @Override
    <B extends T> T getOrElse(Closure<B> c) {
        return o
    }

    @Override
    <B> Option<B> collect(Closure<B> c) {
        return of(c(o))
    }

    @Override
    Option<T> each(Closure c) {
        c(o)
        this
    }

    @Override
    Iterator<T> iterator() {
        return new Iterator<T>() {
            def one = o

            @Override
            boolean hasNext() {
                one != null
            }

            @Override
            Object next() {
                def ret = one
                one = null
                return ret
            }

            @Override
            void remove() {
            }
        }
    }
}

class None<T> extends Option<T> {

    private None() {}

    static None INSTANCE = new None()

    @Override
    <B extends T> Option<B> orElse(Closure<B> c) {
        return of(c())
    }

    @Override
    <B extends T> B getOrElse(Closure<B> c) {
        return c()
    }

    @Override
    <B> Option<B> collect(Closure<B> c) {
        return this
    }

    @Override
    Option<T> each(Closure c) {
        this
    }

    @Override
    Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            boolean hasNext() {
                return false
            }

            @Override
            Object next() {
                return null
            }

            @Override
            void remove() {
            }
        }
    }
}
