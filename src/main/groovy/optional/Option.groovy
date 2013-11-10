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

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * The class Option for Groovy inspired from Scala.
 */
abstract class Option<T> implements Iterable<T> {
    static <A> Option<A> of(A o) {
        if (o) {
            new Some(o)
        } else {
            None.INSTANCE
        }
    }

    abstract T get()

    abstract boolean isDefined()

    abstract <B extends T> Option<B> orElse(Closure<B> c)

    abstract <B extends T> B getOrElse(Closure<B> c)

    abstract <B> Option<B> collect(Closure<B> c)

    abstract Option<T> each(Closure c)
}

@TupleConstructor(includeFields = true)
@EqualsAndHashCode(includeFields = true)
@ToString(includeFields = true)
class Some<T> extends Option<T> {
    private final T m_O

    @Override
    T get() {
        m_O
    }

    @Override
    boolean isDefined() {
        true
    }

    @Override
    <B extends T> Option<T> orElse(Closure<B> c) {
        this
    }

    @Override
    <B extends T> T getOrElse(Closure<B> c) {
        m_O
    }

    @Override
    <B> Option<B> collect(Closure<B> c) {
        return of(c(m_O))
    }

    @Override
    Option<T> each(Closure c) {
        c(m_O)
        this
    }

    @Override
    Iterator<T> iterator() {
        new Iterator<T>() {
            def one = m_O

            @Override
            boolean hasNext() {
                one != null
            }

            @Override
            Object next() {
                def ret = one
                one = null
                ret
            }

            @Override
            void remove() {
            }
        }
    }
}

@ToString
class None<T> extends Option<T> {

    private None() {}

    static None INSTANCE = new None()

    @Override
    T get() {
        throw new NoSuchElementException("None.get")
    }

    @Override
    boolean isDefined() {
        false
    }

    @Override
    <B extends T> Option<B> orElse(Closure<B> c) {
        of(c())
    }

    @Override
    <B extends T> B getOrElse(Closure<B> c) {
        c()
    }

    @Override
    <B> Option<B> collect(Closure<B> c) {
        this
    }

    @Override
    Option<T> each(Closure c) {
        this
    }

    @Override
    Iterator<T> iterator() {
        new Iterator<T>() {
            @Override
            boolean hasNext() {
                false
            }

            @Override
            Object next() {
                null
            }

            @Override
            void remove() {
            }
        }
    }
}
