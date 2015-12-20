package blox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class Count extends TestCase {

    public void testLevel1() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.down, Input.right, Input.right, Input.right,
                Input.down);
        doTest("level1.txt", inputs);
    }

    public void testLevel2() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.right, Input.down, Input.right, Input.right, Input.right,
                Input.right, Input.up, Input.up, Input.down, Input.right, Input.down, Input.right, Input.right,
                Input.up, Input.right, Input.up);
        doTest("level2.txt", inputs);
    }

    public void testLevel3() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.right, Input.right, Input.right, Input.up,
                Input.left, Input.down, Input.right, Input.up, Input.up, Input.right, Input.right, Input.right,
                Input.down, Input.down, Input.down, Input.right, Input.up);
        doTest("level3.txt", inputs);
    }

    public void testLevel4() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.up, Input.right, Input.right, Input.up,
                Input.right, Input.right, Input.right, Input.right, Input.right, Input.right, Input.down, Input.right,
                Input.down, Input.down, Input.down, Input.down, Input.down, Input.right, Input.up, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.down);
        doTest("level4.txt", inputs);
    }

    public void testLevel5() throws IOException {
        List<Input> inputs = Arrays.asList(Input.left, Input.left, Input.left, Input.right, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.right, Input.down, Input.down, Input.right,
                Input.right, Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.left, Input.left, Input.left, Input.left, Input.down, Input.left, Input.left, Input.left,
                Input.left, Input.left, Input.left);
        doTest("level5.txt", inputs);
    }

    /**
     * @throws IOException
     */
    public void testLevel6() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.down, Input.down, Input.right,
                Input.down, Input.down, Input.right, Input.down, Input.right, Input.up, Input.left, Input.left,
                Input.left, Input.up, Input.up, Input.left, Input.up, Input.up, Input.up, Input.right, Input.right,
                Input.down, Input.right, Input.right, Input.up, Input.left, Input.down, Input.down, Input.right,
                Input.right, Input.down, Input.down, Input.right);
        doTest("level6.txt", inputs);
    }

    /**
     * single strong switch
     */
    public void testLevel7() throws IOException {
        List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.up, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.down, Input.right, Input.left, Input.up, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.right, Input.down, Input.right, Input.down,
                Input.right, Input.right, Input.right, Input.up, Input.up, Input.right, Input.down, Input.left,
                Input.up, Input.right, Input.up, Input.up, Input.right, Input.right, Input.right, Input.down,
                Input.right, Input.down, Input.right, Input.down, Input.left, Input.up);
        doTest("level7.txt", inputs);
    }

    // single teleport
    public void testLevel8() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.down, Input.down, Input.down, Input.down,
                Input.down, Input.up, Input.right, Input.up, Input.right);
        doTest("level8.txt", inputs);
    }

    public void testLevel9() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.up, Input.right, Input.left, Input.down, Input.left, Input.left,
                Input.left, Input.left, Input.up, Input.nextBlock, Input.right, Input.down, Input.right, Input.right,
                Input.right, Input.right, Input.down);
        doTest("level9.txt", inputs);
    }

    public void testLevel10() throws IOException {
        List<Input> inputs = Arrays.asList();

        inputs = Arrays.asList(Input.right, Input.right, Input.left, Input.down, Input.down, Input.down, Input.right,
                Input.down, Input.down, Input.down, Input.down, Input.down, Input.left, Input.left, Input.left,
                Input.left, Input.up, Input.left, Input.left, Input.left, Input.down, Input.up, Input.right,
                Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.up, Input.nextBlock,
                Input.right, Input.down, Input.right, Input.down, Input.down, Input.right, Input.down, Input.down,
                Input.down, Input.left, Input.down, Input.up, Input.right, Input.up, Input.up, Input.up, Input.up,
                Input.left, Input.left, Input.up, Input.left, Input.down, Input.left, Input.left, Input.left,
                Input.left, Input.left);
        doTest("level10.txt", inputs);
    }

    public void testLevel11() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.up, Input.left,
                Input.down, Input.down, Input.down, Input.right, Input.right, Input.right, Input.down, Input.left,
                Input.up, Input.left, Input.left, Input.left, Input.up, Input.up, Input.right, Input.up, Input.right,
                Input.right, Input.down, Input.right, Input.up, Input.left, Input.left, Input.left, Input.down,
                Input.down, Input.left, Input.left, Input.left, Input.up, Input.up, Input.right, Input.up, Input.up,
                Input.left, Input.down, Input.right, Input.up, Input.right, Input.down, Input.left);
        doTest("level11.txt", inputs);
    }

    public void testLevel12() throws IOException {
        List<Input> inputs = Arrays.asList(Input.left, Input.down, Input.right, Input.up, Input.right, Input.up,
                Input.right, Input.up, Input.left, Input.down, Input.right, Input.up, Input.right, Input.up,
                Input.right, Input.right, Input.right, Input.down, Input.down, Input.down, Input.left, Input.up,
                Input.right, Input.up, Input.up, Input.up, Input.left, Input.left, Input.left, Input.right,
                Input.right, Input.right, Input.down, Input.down, Input.down, Input.left, Input.down, Input.right,
                Input.up, Input.right, Input.down, Input.left, Input.up, Input.right, Input.up, Input.up, Input.right,
                Input.up, Input.down, Input.left, Input.down, Input.down, Input.down, Input.left, Input.up,
                Input.right, Input.up, Input.left, Input.up, Input.left, Input.left, Input.left, Input.down,
                Input.down, Input.left);
        doTest("level12.txt", inputs);
    }

    public void testLevel13() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left, Input.down, Input.right, Input.down,
                Input.right, Input.down, Input.left, Input.up, Input.up, Input.up, Input.up, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.down, Input.down, Input.down,
                Input.right, Input.up, Input.left, Input.down, Input.right, Input.down, Input.down, Input.right,
                Input.down, Input.down, Input.right, Input.up, Input.right, Input.right, Input.right, Input.right,
                Input.right, Input.up, Input.left, Input.down, Input.left, Input.up, Input.up);
        doTest("level13.txt", inputs);
    }

    public void testLevel14() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.up, Input.left,
                Input.left, Input.down, Input.right, Input.right, Input.right, Input.down, Input.down, Input.down,
                Input.down, Input.left, Input.left, Input.left, Input.down, Input.right, Input.up, Input.right,
                Input.right, Input.up, Input.up, Input.down, Input.right, Input.down, Input.left, Input.left,
                Input.down, Input.down, Input.right, Input.right, Input.left, Input.left, Input.up, Input.left,
                Input.left, Input.up, Input.right, Input.right, Input.right, Input.up, Input.up, Input.up, Input.up,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.left, Input.up, Input.right,
                Input.down, Input.left, Input.left, Input.left, Input.left, Input.left, Input.down, Input.down,
                Input.down, Input.right, Input.down, Input.right);
        doTest("level14.txt", inputs);
    }

    public void testLevel15() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.up, Input.up,
                Input.nextBlock, Input.up, Input.up, Input.up, Input.up, Input.up, Input.right, Input.right,
                Input.right, Input.up, Input.up, Input.right, Input.right, Input.right, Input.right, Input.down,
                Input.down, Input.up, Input.left, Input.up, Input.nextBlock, Input.left, Input.left, Input.left,
                Input.left, Input.down, Input.left, Input.down, Input.up, Input.nextBlock, Input.left, Input.left,
                Input.left, Input.down, Input.nextBlock, Input.up, Input.left, Input.left, Input.left, Input.left,
                Input.down, Input.left, Input.left, Input.down, Input.down, Input.down, Input.left, Input.down,
                Input.right, Input.up, Input.right, Input.right, Input.right, Input.right, Input.right, Input.right,
                Input.right);
        doTest("level15.txt", inputs);
    }

    public void testLevel16() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.nextBlock,
                Input.right, Input.down, Input.nextBlock, Input.right, Input.up, Input.nextBlock, Input.right,
                Input.left, Input.left, Input.left, Input.right, Input.right, Input.right, Input.right, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.down, Input.nextBlock, Input.right, Input.up,
                Input.nextBlock, Input.right, Input.right, Input.right, Input.right);
        doTest("level16.txt", inputs);
    }

    public void testLevel17() throws IOException {
        List<Input> inputs = Arrays.asList(Input.down, Input.down, Input.down, Input.down, Input.down, Input.left,
                Input.up, Input.right, Input.right, Input.right, Input.right, Input.right, Input.up, Input.right,
                Input.right, Input.right, Input.left, Input.left, Input.left, Input.down, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.up, Input.up, Input.up, Input.up, Input.right, Input.right,
                Input.right, Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.down, Input.left, Input.up, Input.right, Input.down, Input.up, Input.right, Input.down,
                Input.left, Input.up, Input.up, Input.right, Input.down, Input.left, Input.left, Input.up, Input.right,
                Input.down, Input.left, Input.left, Input.left, Input.left, Input.up, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.down, Input.down, Input.down, Input.right,
                Input.right, Input.right, Input.right, Input.right, Input.up, Input.right, Input.right, Input.right,
                Input.down, Input.down, Input.up, Input.left, Input.up, Input.left, Input.left, Input.left, Input.down,
                Input.left, Input.left, Input.left, Input.up, Input.left, Input.up, Input.up, Input.up, Input.right,
                Input.right, Input.right, Input.right, Input.right, Input.down, Input.right, Input.right, Input.right);
        doTest("level17.txt", inputs);
    }

    public void testLevel18() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.down, Input.right, Input.right, Input.right,
                Input.right, Input.up, Input.up, Input.down, Input.down, Input.left, Input.left, Input.left, Input.up,
                Input.right, Input.down, Input.left, Input.left, Input.up, Input.right, Input.down, Input.right,
                Input.right, Input.right, Input.down, Input.down, Input.up, Input.up, Input.left, Input.left,
                Input.left, Input.up, Input.left, Input.down, Input.right, Input.up, Input.left, Input.left,
                Input.down, Input.down, Input.down, Input.down, Input.right, Input.right, Input.left, Input.left,
                Input.up, Input.up, Input.up, Input.up, Input.right, Input.right, Input.down, Input.left, Input.up,
                Input.right, Input.down, Input.down, Input.left, Input.up, Input.right, Input.down, Input.right,
                Input.up, Input.right, Input.right, Input.right, Input.right, Input.right, Input.down, Input.down,
                Input.down, Input.right, Input.up, Input.left, Input.down, Input.down, Input.right, Input.up,
                Input.left, Input.down, Input.left, Input.up, Input.right);
        doTest("level18.txt", inputs);
    }

    public void testLevel19() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.right, Input.down, Input.right, Input.up, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.down, Input.right, Input.up, Input.right,
                Input.right, Input.right, Input.right, Input.right, Input.down, Input.down, Input.down, Input.down,
                Input.down, Input.left, Input.left, Input.left, Input.left, Input.left, Input.down, Input.left,
                Input.up, Input.right, Input.right, Input.right, Input.right, Input.right, Input.right, Input.up,
                Input.left, Input.down, Input.left, Input.left, Input.left, Input.left, Input.left, Input.down,
                Input.down, Input.down, Input.down, Input.right, Input.right, Input.right, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.up, Input.up);
        doTest("level19.txt", inputs);
    }

    public void testLevel20() throws IOException {
        List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.down, Input.right, Input.down, Input.left,
                Input.up, Input.up, Input.right, Input.up, Input.up, Input.left, Input.left, Input.left, Input.left,
                Input.down, Input.down, Input.right, Input.up, Input.left, Input.left, Input.down, Input.down,
                Input.down, Input.left, Input.down, Input.up, Input.right, Input.up, Input.up, Input.right, Input.up,
                Input.up, Input.right, Input.right, Input.right, Input.down, Input.left, Input.down, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.down, Input.down, Input.nextBlock, Input.up,
                Input.left, Input.down, Input.nextBlock, Input.down, Input.down, Input.right, Input.right, Input.right,
                Input.right, Input.down);
        doTest("level20.txt", inputs);
    }

    public void testLevel21() throws IOException {
        List<Input> inputs = Arrays
                .asList(Input.right, Input.down, Input.left, Input.up, Input.left, Input.down, Input.right, Input.up,
                        Input.right, Input.right, Input.up, Input.right, Input.right, Input.right, Input.up,
                        Input.left, Input.down, Input.down, Input.down, Input.up, Input.up, Input.up, Input.right,
                        Input.down, Input.left, Input.left, Input.left, Input.down, Input.left, Input.left, Input.down,
                        Input.left, Input.up, Input.right, Input.down, Input.right, Input.up, Input.left, Input.down,
                        Input.right, Input.up, Input.left, Input.down, Input.right, Input.down, Input.down,
                        Input.right, Input.down, Input.down, Input.right, Input.right, Input.right, Input.up,
                        Input.right, Input.down, Input.left, Input.left, Input.left, Input.left, Input.up, Input.right,
                        Input.down, Input.right, Input.right, Input.right, Input.up, Input.up, Input.up, Input.up,
                        Input.right, Input.right, Input.right);
        doTest("level21.txt", inputs);
    }

    public void testLevel22() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.up, Input.right, Input.right, Input.right,
                Input.down, Input.right, Input.right, Input.down, Input.down, Input.down, Input.left, Input.down,
                Input.up, Input.right, Input.up, Input.up, Input.up, Input.left, Input.up, Input.right, Input.down,
                Input.left, Input.up, Input.left, Input.up, Input.left, Input.left, Input.down, Input.left, Input.left,
                Input.left, Input.down, Input.right, Input.up, Input.right, Input.down, Input.left, Input.down,
                Input.down, Input.down, Input.right, Input.down, Input.up, Input.left, Input.up, Input.up, Input.up,
                Input.right, Input.up, Input.left, Input.down, Input.right, Input.up, Input.right, Input.up,
                Input.right, Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.up);

        inputs = Arrays.asList(Input.right, Input.up, Input.right, Input.right, Input.right, Input.right, Input.down,
                Input.right, Input.down, Input.down, Input.down, Input.left, Input.down, Input.up, Input.right,
                Input.up, Input.up, Input.up, Input.left, Input.up, Input.right, Input.down, Input.left, Input.up,
                Input.left, Input.up, Input.left, Input.left, Input.down, Input.left, Input.left, Input.left,
                Input.down, Input.right, Input.up, Input.right, Input.down, Input.left, Input.down, Input.down,
                Input.down, Input.right, Input.down, Input.up, Input.left, Input.up, Input.up, Input.up, Input.right,
                Input.up, Input.left, Input.down, Input.right, Input.up, Input.right, Input.up, Input.right,
                Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right, Input.up);
        doTest("level22.txt", inputs);
    }

    public void testLevel23() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.down, Input.right, Input.up, Input.right,
                Input.right, Input.right, Input.right, Input.right, Input.up, Input.left, Input.down, Input.right,
                Input.left, Input.up, Input.left, Input.left, Input.up, Input.up, Input.up, Input.up, Input.right,
                Input.right, Input.right, Input.right, Input.up, Input.left, Input.down, Input.left, Input.left,
                Input.left, Input.down, Input.down, Input.down, Input.left, Input.down, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.left, Input.up, Input.up, Input.up,
                Input.right, Input.right, Input.up, Input.down, Input.left, Input.up, Input.right, Input.down,
                Input.down, Input.right, Input.down, Input.down, Input.down, Input.right, Input.right, Input.down,
                Input.right, Input.up, Input.up, Input.up, Input.up, Input.up, Input.up, Input.right, Input.down,
                Input.left, Input.up);
        doTest("level23.txt", inputs);
    }

    public void testLevel24() throws IOException {
        List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.down, Input.right, Input.down, Input.left,
                Input.up, Input.up, Input.down, Input.down, Input.right, Input.up, Input.left, Input.up, Input.right,
                Input.down, Input.right, Input.right, Input.up, Input.up, Input.right, Input.up, Input.left,
                Input.down, Input.right, Input.up, Input.right, Input.right, Input.right, Input.right, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.down, Input.right, Input.left, Input.up,
                Input.right, Input.down, Input.left, Input.up, Input.right, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.right, Input.down, Input.left, Input.right, Input.up, Input.right,
                Input.right, Input.right);
        doTest("level24.txt", inputs);
    }

    public void testLevel25() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.right, Input.down, Input.left, Input.up, Input.right,
                Input.down, Input.left, Input.up, Input.right, Input.right, Input.right, Input.right, Input.up,
                Input.left, Input.up, Input.left, Input.left, Input.up, Input.right, Input.left, Input.down,
                Input.right, Input.right, Input.down, Input.right, Input.down, Input.left, Input.left, Input.left,
                Input.left, Input.down, Input.right, Input.up, Input.right, Input.right, Input.right, Input.up,
                Input.up, Input.right, Input.right, Input.right, Input.up, Input.right, Input.down, Input.left,
                Input.up, Input.up, Input.right, Input.down, Input.left, Input.up, Input.right, Input.down, Input.left);
        doTest("level25.txt", inputs);
    }

    public void testLevel26() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left, Input.left, Input.left, Input.down,
                Input.left, Input.left, Input.left, Input.down, Input.right, Input.up, Input.right, Input.right,
                Input.up, Input.up, Input.right, Input.right, Input.down, Input.right, Input.right, Input.down,
                Input.right, Input.right, Input.up, Input.up, Input.left, Input.left, Input.up, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.left, Input.left, Input.left, Input.down,
                Input.left, Input.down, Input.down, Input.nextBlock, Input.up, Input.up, Input.up, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.down, Input.left, Input.left,
                Input.down, Input.down, Input.left, Input.down, Input.up, Input.up, Input.right, Input.up, Input.right,
                Input.right, Input.right, Input.up, Input.right, Input.right, Input.right, Input.down, Input.right,
                Input.right, Input.up, Input.up, Input.left, Input.left, Input.up, Input.left, Input.left, Input.left,
                Input.down, Input.left, Input.down, Input.down, Input.down, Input.right, Input.right, Input.down,
                Input.right, Input.nextBlock, Input.up, Input.up, Input.up, Input.left, Input.left, Input.left,
                Input.down, Input.left, Input.down, Input.down, Input.down, Input.right, Input.right, Input.down,
                Input.left);
        doTest("level26.txt", inputs);
    }

    public void testLevel27() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.right, Input.up,
                Input.left, Input.down, Input.right, Input.up, Input.right, Input.down, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.up, Input.right, Input.down, Input.left,
                Input.left, Input.up, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.up, Input.right, Input.right, Input.right, Input.right, Input.down, Input.down, Input.down,
                Input.left, Input.down, Input.down, Input.down, Input.left, Input.left, Input.left, Input.down,
                Input.left, Input.up, Input.up, Input.up, Input.up, Input.right, Input.down, Input.left, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.up, Input.left, Input.down,
                Input.down, Input.right, Input.up, Input.left, Input.down, Input.right, Input.up, Input.left);
        doTest("level27.txt", inputs);
    }

    public void testLevel28() throws IOException {
        List<Input> inputs = Arrays.asList(Input.left, Input.down, Input.down, Input.down, Input.right, Input.down,
                Input.left, Input.up, Input.right, Input.down, Input.down, Input.right, Input.right, Input.down,
                Input.down, Input.right, Input.right, Input.right, Input.up, Input.up, Input.up, Input.left, Input.up,
                Input.left, Input.up, Input.left, Input.up, Input.left, Input.down, Input.right, Input.right,
                Input.down, Input.right, Input.down, Input.right, Input.down, Input.right, Input.right, Input.down,
                Input.left, Input.down, Input.left, Input.left, Input.up, Input.up, Input.up, Input.left, Input.up,
                Input.left, Input.up, Input.left, Input.left, Input.up, Input.left, Input.left, Input.down, Input.left,
                Input.up, Input.right, Input.right, Input.right, Input.down, Input.right, Input.down, Input.right,
                Input.down, Input.right, Input.down, Input.right, Input.left, Input.left, Input.left, Input.left,
                Input.down, Input.down, Input.down, Input.left, Input.left, Input.left, Input.left, Input.up,
                Input.left, Input.up, Input.left, Input.left, Input.left, Input.down, Input.nextBlock, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.up, Input.left, Input.up, Input.left,
                Input.left, Input.left, Input.up);
        doTest("level28.txt", inputs);
    }

    public void testLevel29() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.left, Input.down, Input.left, Input.up,
                Input.right, Input.down, Input.right, Input.down, Input.down, Input.right, Input.right, Input.left,
                Input.left, Input.up, Input.up, Input.left, Input.up, Input.right, Input.down, Input.left, Input.up,
                Input.left, Input.down, Input.left, Input.up, Input.up, Input.left, Input.left, Input.right,
                Input.right, Input.down, Input.down, Input.right, Input.up, Input.right, Input.down, Input.right,
                Input.up, Input.left, Input.down, Input.right, Input.up, Input.up, Input.right, Input.right,
                Input.left, Input.left, Input.down, Input.down, Input.down, Input.down, Input.down, Input.down,
                Input.right, Input.right, Input.left, Input.left, Input.up, Input.up, Input.up, Input.up, Input.left,
                Input.left, Input.left, Input.left, Input.left, Input.left, Input.right, Input.right, Input.right,
                Input.right, Input.down, Input.left, Input.up, Input.right, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.left, Input.left, Input.left, Input.left, Input.left, Input.up,
                Input.left, Input.down, Input.down, Input.down, Input.down, Input.left, Input.left, Input.left,
                Input.down, Input.right, Input.up, Input.left, Input.down, Input.right, Input.up, Input.left);
        doTest("level29.txt", inputs);
    }

    public void testLevel30() throws IOException {
        List<Input> inputs = Arrays.asList(Input.down, Input.right, Input.down, Input.down, Input.right, Input.right,
                Input.up, Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.right,
                Input.up, Input.down, Input.left, Input.left, Input.left, Input.left, Input.up, Input.left, Input.left,
                Input.left, Input.down, Input.down, Input.left, Input.up, Input.right, Input.right, Input.right,
                Input.right, Input.right, Input.right, Input.up, Input.up, Input.up, Input.right, Input.right,
                Input.right, Input.up, Input.right, Input.down, Input.down, Input.down, Input.left, Input.left,
                Input.down, Input.left, Input.down, Input.right, Input.up, Input.up, Input.right, Input.down,
                Input.left, Input.down, Input.left, Input.left, Input.left, Input.left, Input.up, Input.left,
                Input.left, Input.down, Input.left, Input.left, Input.up, Input.left, Input.down, Input.left, Input.up,
                Input.right, Input.up, Input.down, Input.left, Input.down, Input.right, Input.up, Input.right,
                Input.down, Input.right, Input.right, Input.up, Input.left, Input.down, Input.down, Input.left,
                Input.up, Input.right, Input.right, Input.right, Input.right, Input.right, Input.right, Input.right,
                Input.up, Input.left, Input.up, Input.up, Input.up, Input.right, Input.right, Input.right, Input.up,
                Input.right, Input.up, Input.left, Input.left, Input.left, Input.left, Input.left, Input.down,
                Input.left);
        doTest("level30.txt", inputs);
    }

    public void testLevel31() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.down, Input.left, Input.left, Input.up,
                Input.up, Input.up, Input.up, Input.down, Input.down, Input.down, Input.down, Input.right, Input.right,
                Input.right, Input.up, Input.left, Input.down, Input.right, Input.up, Input.up, Input.up, Input.up,
                Input.right, Input.up, Input.left, Input.down, Input.down, Input.right, Input.up, Input.left,
                Input.down, Input.left, Input.left, Input.left, Input.down, Input.down, Input.down, Input.left,
                Input.down, Input.down, Input.up, Input.up, Input.right, Input.up, Input.up, Input.up, Input.left,
                Input.left, Input.left, Input.left, Input.down, Input.right, Input.up, Input.left, Input.down,
                Input.down, Input.down, Input.down, Input.down, Input.left, Input.up, Input.right, Input.left,
                Input.up, Input.right, Input.down, Input.right, Input.up, Input.right, Input.right, Input.up,
                Input.right, Input.right, Input.down, Input.right, Input.right, Input.right, Input.up, Input.left,
                Input.down, Input.right, Input.up, Input.up, Input.up, Input.up, Input.up, Input.right, Input.down,
                Input.left);
        doTest("level31.txt", inputs);
    }

    public void testLevel32() throws IOException {
        List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left, Input.down, Input.right, Input.up,
                Input.right, Input.up, Input.right, Input.down, Input.left, Input.right, Input.up, Input.left,
                Input.down, Input.left, Input.down, Input.left, Input.up, Input.right, Input.down, Input.left,
                Input.up, Input.right, Input.up, Input.right, Input.up, Input.right, Input.down, Input.left,
                Input.left, Input.down, Input.down, Input.down, Input.left, Input.down, Input.left, Input.left,
                Input.up, Input.left, Input.down, Input.right, Input.right, Input.right, Input.up, Input.up, Input.up,
                Input.up, Input.up, Input.right, Input.up, Input.right, Input.right, Input.up, Input.down, Input.left,
                Input.left, Input.down, Input.left, Input.down, Input.down, Input.down, Input.down, Input.down,
                Input.left, Input.left, Input.left, Input.left, Input.up, Input.right, Input.left, Input.down,
                Input.right, Input.right, Input.right, Input.right, Input.up, Input.up, Input.up, Input.up, Input.up,
                Input.right, Input.up, Input.right, Input.right, Input.up, Input.down, Input.left, Input.left,
                Input.down, Input.left, Input.down, Input.down, Input.down, Input.down, Input.down, Input.left,
                Input.left, Input.left, Input.up, Input.right, Input.down, Input.right, Input.right, Input.up,
                Input.right, Input.up, Input.up, Input.up, Input.right, Input.right, Input.up, Input.left, Input.down,
                Input.left, Input.down, Input.left, Input.down, Input.right, Input.up, Input.left, Input.left,
                Input.up, Input.left, Input.left, Input.left, Input.left, Input.left, Input.down);
        doTest("level32.txt", inputs);
    }

    public void testLevel33() throws IOException {
        List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.right, Input.right, Input.up, Input.left,
                Input.down, Input.down, Input.left, Input.up, Input.right, Input.up, Input.up, Input.right,
                Input.right, Input.down, Input.right, Input.down, Input.right, Input.up, Input.left, Input.left,
                Input.up, Input.right, Input.down, Input.right, Input.down, Input.down, Input.right, Input.down,
                Input.left, Input.down, Input.down, Input.right, Input.right, Input.up, Input.down, Input.left,
                Input.left, Input.up, Input.up, Input.right, Input.up, Input.left, Input.up, Input.up, Input.left,
                Input.up, Input.left, Input.down, Input.right, Input.up, Input.left, Input.left, Input.left,
                Input.down, Input.down, Input.down, Input.left, Input.down, Input.left, Input.left, Input.down,
                Input.left, Input.up);
        doTest("level33.txt", inputs);
    }

    private List<Input> filter(List<Input> asInputList) {
        List<Input> inputs = new ArrayList<Input>(asInputList.size());
        for (Input input : asInputList) {
            if( input != Input.nextBlock ) {
                inputs.add(input);
            }
        }
        return inputs;
    }

    static int count = 0;
    
    private void doTest(String scapeName, List<Input> inputs) throws IOException {
    	List<Input> filter = filter(inputs);
		int size = filter.size();
		count += size;
    	System.out.println(String.format("%s : %s : %s : %s", scapeName, toString(inputs), size, count));
    }

	private List<String> toString(List<Input> inputs) {
		List<String> ll = new ArrayList<String>();
		int count = 0;
		Input last = inputs.get(0);
		
		for (Input input : inputs) {
			if (input != last) {
				ll.add(toString(last, count));
				count = 0;
			}
			count++;
			last = input;
		}
		
		ll.add(toString(last, count));
		return ll;
	}

	private String toString(Input input, int count) {
		String s = toString(input);
		return count == 1 ? s : s + count;
	}
	
	private String toString(Input input) {
		switch(input) {
		case down:
			return "D";
		case up:
			return "U";
		case left:
			return "L";
		case right:
			return "R";
		case nextBlock:
		default:
			return "S";
		}
	}
}
