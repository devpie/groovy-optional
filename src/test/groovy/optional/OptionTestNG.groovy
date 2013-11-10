package optional

/**
 * @author Jan Marco Müller
 */
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.Assert.assertEquals

class OptionTestNG {

    @Test(dataProvider = "data")
    void shouldBe(Map d) {
        assertEquals(d.init, d.expected)
    }

    @Test(dataProvider = "data2")
    void collectTest(Map d) {
        assertEquals(
                d.init.collect(d.collect),
                d.expected
        )
    }

    @Test(dataProvider = "data3")
    void orElseTest(Map d) {
        assertEquals(
                d.init.orElse(d.orElse),
                d.expected
        )
    }

    @Test(dataProvider = "data4")
    void getOrElseTest(Map d) {
        assertEquals(
                d.init.getOrElse(d.getOrElse),
                d.expected
        )
    }

    @Test(dataProvider = "data5")
    void getAndIsDefined(Map d) {
        assertEquals(
                d.init.isDefined(),
                d.isDefined
        )
        try {
            assertEquals(
                    d.init.get(),
                    d.expected
            )
        } catch (ex) {
            assertEquals(ex.class, d.expectedException.class)
            assertEquals(ex.message, d.expectedException.message)
        }
    }


    @DataProvider(name = "data", parallel = true)
    Object[][] provide() {
        [
                [[init: Option.of(null), expected: None.INSTANCE]].toArray(),
                [[init: Option.of("Hello"), expected: new Some("Hello")]].toArray(),
                [[init: Option.of(5), expected: new Some(5)]].toArray()
        ].toArray()
    }

    @DataProvider(name = "data2", parallel = true)
    Object[][] provide2() {
        [
                [[
                        init: Option.of(null),
                        collect: { it + it },
                        expected: None.INSTANCE
                ]].toArray(),
                [[
                        init: Option.of("Hello"),
                        collect: { it + it },
                        expected: new Some("HelloHello")
                ]].toArray(),
                [[
                        init: Option.of(5),
                        collect: { it + it },
                        expected: new Some(10)
                ]].toArray()
        ].toArray()
    }

    @DataProvider(name = "data3", parallel = true)
    Object[][] provide3() {
        [
                [[
                        init: Option.of(null),
                        orElse: { "test" },
                        expected: new Some("test")
                ]].toArray(),
                [[
                        init: Option.of("Hello"),
                        orElse: { "test" },
                        expected: new Some("Hello")
                ]].toArray(),
                [[
                        init: Option.of(5),
                        orElse: { "test" },
                        expected: new Some(5)
                ]].toArray(),
                [[
                        init: Option.of(null),
                        orElse: { 66 },
                        expected: new Some(66)
                ]].toArray()
        ].toArray()
    }

    @DataProvider(name = "data4", parallel = true)
    Object[][] provide4() {
        [
                [[
                        init: Option.of(null),
                        getOrElse: { "test" },
                        expected: "test"
                ]].toArray(),
                [[
                        init: Option.of("Hello"),
                        getOrElse: { "test" },
                        expected: "Hello"
                ]].toArray(),
                [[
                        init: Option.of(5),
                        getOrElse: { "test" },
                        expected: 5
                ]].toArray(),
                [[
                        init: Option.of(null),
                        getOrElse: { 66 },
                        expected: 66
                ]].toArray()
        ].toArray()
    }

    @DataProvider(name = "data5", parallel = true)
    Object[][] provide5() {
        [
                [[
                        init: Option.of(null),
                        isDefined: false,
                        expected: null,
                        expectedException: new NoSuchElementException("None.get")
                ]].toArray(),
                [[
                        init: Option.of("Hello"),
                        isDefined: true,
                        expected: "Hello",
                        expectedException: null
                ]].toArray(),
                [[
                        init: Option.of(5),
                        isDefined: true,
                        expected: 5,
                        expectedException: null
                ]].toArray()
        ].toArray()
    }

}
