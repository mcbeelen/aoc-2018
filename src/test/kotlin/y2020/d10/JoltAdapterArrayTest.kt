package y2020.d10

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class JoltAdapterArrayTest {


    @Test
    fun examplePartOne() {
        assertThat(multiplcationOfFrequencyOfOnesAndThrees(firstExample), equalTo(35))
        assertThat(multiplcationOfFrequencyOfOnesAndThrees(additionalExample), equalTo(220))
    }

    @Test
    fun partOne() {
        assertThat(multiplcationOfFrequencyOfOnesAndThrees(y2020d10input), equalTo(2030))
    }

    @Test
    fun examplePartTwo() {
        assertThat(countDistinctWaysToArrangeAdapters(firstExample), equalTo(8))
        assertThat(countDistinctWaysToArrangeAdapters(additionalExample), equalTo(19208))
    }

    @Test
    fun partTwo() {
        assertThat(countDistinctWaysToArrangeAdapters(y2020d10input), equalTo(42_313_823_813_632))
    }
}

const val firstExample = """16
10
15
5
1
11
7
19
6
12
4"""



const val additionalExample = """28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3
"""

