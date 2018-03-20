package com.hhp.ml.algo.classification;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hhp.ml.training.classification.Classification;
import com.hhp.ml.training.classification.Classifier;

public class BayesClassifierTest {

    private static final double EPSILON = 0.001;
    private static final String CATEGORY_NEGATIVE = "negative";
    private static final String CATEGORY_POSITIVE = "positive";
    private Classifier<String, String> bayes;

    @Before
    public void setUp() {
        /*
         * Create a new classifier instance. The context features are Strings
         * and the context will be classified with a String according to the
         * featureset of the context.
         */
        bayes = new BayesClassifier<String, String>();

        /*
         * The classifier can learn from classifications that are handed over to
         * the learn methods. Imagin a tokenized text as follows. The tokens are
         * the text's features. The category of the text will either be positive
         * or negative.
         */
        final String[] positiveText = "I love sunny days".split("\\s");
        bayes.learn(CATEGORY_POSITIVE, Arrays.asList(positiveText));

        final String[] negativeText = "I hate rain".split("\\s");
        bayes.learn(CATEGORY_NEGATIVE, Arrays.asList(negativeText));
    }

    @Test
    public void testStringClassification() {
    	String unknownText1 = "today is a sunny day";
    	String unknownText2 = "there will be rain";

        Assert.assertEquals(CATEGORY_POSITIVE, bayes.classify(Arrays.asList(unknownText1.split("\\s")), unknownText1).getCategory());
        Assert.assertEquals(CATEGORY_NEGATIVE, bayes.classify(Arrays.asList(unknownText2.split("\\s")), unknownText2).getCategory());
    }

    @Test
    public void testStringClassificationInDetails() {

    	String unknownText1 = "today is a sunny day";

        Collection<Classification<String, String>> classifications = ((BayesClassifier<String, String>) bayes)
                .classifyDetailed(Arrays.asList(unknownText1.split("\\s")), unknownText1);

        List<Classification<String, String>> list = new ArrayList<Classification<String, String>>(classifications);

        Assert.assertEquals(CATEGORY_NEGATIVE, list.get(0).getCategory());
        System.out.println(list.get(0).getProbability());
        Assert.assertEquals(0.0078125, list.get(0).getProbability(), EPSILON);

        Assert.assertEquals(CATEGORY_POSITIVE, list.get(1).getCategory());
        System.out.println(list.get(1).getProbability());
        Assert.assertEquals(0.0234375, list.get(1).getProbability(), EPSILON);
    }

    @Test
    public void testSerialization() throws IOException {

        new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(bayes);
    }
}