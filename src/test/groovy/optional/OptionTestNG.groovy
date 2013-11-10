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

}
