package com.hhp.ml.algo.classification;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.hhp.ml.algo.classification.BayesClassifier;
import com.hhp.ml.training.classification.Classification;
import com.hhp.ml.training.classification.Classifier;

public class TestBayesClassifierChatBot {

    private static final double EPSILON = 0.001;
    private Classifier<String, String> bayes;

    public void learn(String samplesConcatinated, String samplesDelimiter, String idDelimiter) throws Exception {
    	bayes 	= new BayesClassifier<String, String>();
    	String[] samples = samplesConcatinated.split(samplesDelimiter);
    	for (String sample : samples) {
			String[] idAndFeatures = sample.split(idDelimiter);
			bayes.learn(idAndFeatures[0], split(idAndFeatures[1]));
		}
    }

    public void writeModel() throws Exception {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("model.bin"));
		os.writeObject(bayes);
		os.close();
	}
	//---------------------------
    public static void main(String[] args) throws Exception {
    	int argIndex = 0;
        if (args.length < 1)
          return;
        String samplesConcatinated 		= null;
        String questionForPrediction 	= null;
        String samplesDelimiter			= null;
        String idDelimiter				= null;
        float probabilityThreshold		= 0f;
        boolean unitTest				= false;
        while (argIndex < args.length && args[argIndex].charAt(0) == '-') {
            if (args[argIndex].equalsIgnoreCase("-samplesConcatinated")) {
            	argIndex++;
            	samplesConcatinated = args[argIndex];
            } else if (args[argIndex].equalsIgnoreCase("-questionForPrediction")) {
            	argIndex++;
            	questionForPrediction = args[argIndex];
            } else if (args[argIndex].equalsIgnoreCase("-samplesDelimiter")) {
            	argIndex++;
            	samplesDelimiter = args[argIndex];
            } else if (args[argIndex].equalsIgnoreCase("-idDelimiter")) {
            	argIndex++;
            	idDelimiter = args[argIndex];
            } else if (args[argIndex].equalsIgnoreCase("-probabilityThreshold")) {
            	argIndex++;
            	try { probabilityThreshold = Float.valueOf(args[argIndex]); } catch (Exception e) {}
            } else if (args[argIndex].equalsIgnoreCase("-unitTest")) {
            	argIndex++;
            	try { unitTest = Boolean.valueOf(args[argIndex]); } catch (Exception e) {}
            }
            argIndex++;
        }
        if (samplesConcatinated == null || questionForPrediction == null || samplesDelimiter == null || idDelimiter == null)
        	return;
        if (unitTest) {
        	//questionForPrediction="641, 642, 643";//good bad ugly
            //questionForPrediction="549, 462, 628";//Find graph locations
        	//questionForPrediction="667, 666, 671, 672, 504, 602";//How do I order toner for my printer? Model number is C736n.
        	questionForPrediction="666, 667, 452, 602, 504";//My printer is not working. Model number is C736n.
            //questionForPrediction="549, 520, 462, 628";//Find graph locations in page
            //questionForPrediction="594, 549, 520, 462";//Find graph positions in the page
            //questionForPrediction="520, 592, 591, 593, 594, 462";//How do I give exact positions for the graph borders on the page? 
        	samplesConcatinated="183#422,423,0,0,0,0,0,0,0@184#424,423,425,0,0,0,0,0,0@185#426,427,0,0,0,0,0,0,0@186#428,429,427,0,0,0,0,0,0@187#426,430,427,0,0,0,0,0,0@188#431,432,427,0,0,0,0,0,0@189#433,427,434,0,0,0,0,0,0@190#435,0,0,0,0,0,0,0,0@191#436,437,438,439,440,427,441,0,0@192#422,442,443,0,0,0,0,0,0@193#427,426,444,445,0,0,0,0,0@194#422,427,0,0,0,0,0,0,0@195#446,444,445,422,427,0,0,0,0@196#422,447,448,427,0,0,0,0,0@197#422,449,450,451,0,0,0,0,0@198#452,453,0,0,0,0,0,0,0@199#454,455,456,427,0,0,0,0,0@200#457,458,459,427,447,0,0,0,0@202#461,462,0,0,0,0,0,0,0@203#462,463,464,465,466,0,0,0,0@204#467,462,427,0,0,0,0,0,0@205#468,469,470,471,472,473,0,0,0@206#462,474,0,0,0,0,0,0,0@207#475,462,476,477,0,0,0,0,0@208#478,479,480,481,0,0,0,0,0@209#482,483,484,440,485,0,0,0,0@210#483,486,487,488,489,0,0,0,0@211#490,491,476,492,493,485,0,0,0@212#494,495,496,0,0,0,0,0,0@213#497,485,498,0,0,0,0,0,0@214#499,500,497,501,502,503,504,461,0@215#505,426,506,507,427,508,0,0,0@216#509,510,464,511,427,512,0,0,0@217#427,470,513,514,0,0,0,0,0@218#427,515,509,0,0,0,0,0,0@219#516,517,509,427,518,0,0,0,0@220#519,520,521,522,0,0,0,0,0@221#523,519,476,509,524,427,0,0,0@222#525,524,521,526,489,0,0,0,0@223#527,521,528,0,0,0,0,0,0@224#529,530,531,532,533,471,0,0,0@225#534,465,535,0,0,0,0,0,0@226#536,537,436,538,0,0,0,0,0@227#539,476,521,472,540,0,0,0,0@228#541,542,489,0,0,0,0,0,0@229#543,541,476,0,0,0,0,0,0@230#544,470,476,0,0,0,0,0,0@231#525,476,489,0,0,0,0,0,0@232#545,546,547,0,0,0,0,0,0@233#548,426,549,436,0,0,0,0,0@234#550,530,551,427,552,0,0,0,0@235#553,554,422,555,427,556,0,0,0@236#557,558,559,560,0,0,0,0,0@237#436,561,427,562,489,0,0,0,0@238#563,564,565,530,0,0,0,0,0@239#436,566,426,448,427,0,0,0,0@240#436,567,445,460,427,568,569,0,0@241#570,571,572,573,0,0,0,0,0@242#574,480,476,575,576,0,0,0,0@243#577,476,433,489,0,0,0,0,0@244#578,444,579,0,0,0,0,0,0@245#482,530,476,470,580,0,0,0,0@246#581,582,476,583,584,585,576,532,0@247#552,429,427,550,0,0,0,0,0@248#586,587,588,560,0,0,0,0,0@249#471,589,590,560,0,0,0,0,0@250#591,462,592,520,593,594,0,0,0@251#452,595,596,460,597,0,0,0,0@252#598,599,476,572,427,0,0,0,0@253#600,601,427,602,0,0,0,0,0@254#603,604,605,606,476,607,427,433,608@255#609,610,611,612,613,592,614,597,0@256#615,525,616,0,0,0,0,0,0@201#422,460,0,0,0,0,0,0,0@257#617,603,429,476,495,427,0,0,0@272#664,649,659,661,669,657,650,667,660,656,504,653,654,665,668,666,452,662,663,651,670,655,648,652,647,658,0,0@273#664,659,661,672,669,657,652,650,667,660,656,504,653,654,665,666,662,668,602,663,651,670,671,655,648,649,647,658";
            System.out.println(questionForPrediction);
        }
        TestBayesClassifierChatBot t = new TestBayesClassifierChatBot();
		t.learn(samplesConcatinated, samplesDelimiter, idDelimiter);
        String predictedId = t.predictQuestionAnswerId(questionForPrediction, probabilityThreshold);
        if (unitTest) {
        	System.out.println(predictedId == null ? "No credible match found.." : "\n"+predictedId);
        }
    }
    /*public static void main(String[] args) throws Exception {
		String samplesConcatinated = "183#422,423,0,0,0,0,0,0,0&184#424,423,425,0,0,0,0,0,0&185#426,427,0,0,0,0,0,0,0&186#428,429,427,0,0,0,0,0,0&187#426,430,427,0,0,0,0,0,0&188#431,432,427,0,0,0,0,0,0&189#433,427,434,0,0,0,0,0,0&190#435,0,0,0,0,0,0,0,0&191#436,437,438,439,440,427,441,0,0&192#422,442,443,0,0,0,0,0,0&193#427,426,444,445,0,0,0,0,0&194#422,427,0,0,0,0,0,0,0&195#446,444,445,422,427,0,0,0,0&196#422,447,448,427,0,0,0,0,0&197#422,449,450,451,0,0,0,0,0&198#452,453,0,0,0,0,0,0,0&199#454,455,456,427,0,0,0,0,0&200#457,458,459,427,447,0,0,0,0&201#422,460,0,0,0,0,0,0,0&202#461,462,0,0,0,0,0,0,0&203#462,463,464,465,466,0,0,0,0&204#467,462,427,0,0,0,0,0,0&205#468,469,470,471,472,473,0,0,0&206#462,474,0,0,0,0,0,0,0&207#475,462,476,477,0,0,0,0,0&208#478,479,480,481,0,0,0,0,0&209#482,483,484,440,485,0,0,0,0&210#483,486,487,488,489,0,0,0,0&211#490,491,476,492,493,485,0,0,0&212#494,495,496,0,0,0,0,0,0&213#497,485,498,0,0,0,0,0,0&214#499,500,497,501,502,503,504,461,0&215#505,426,506,507,427,508,0,0,0&216#509,510,464,511,427,512,0,0,0&217#427,470,513,514,0,0,0,0,0&218#427,515,509,0,0,0,0,0,0&219#516,517,509,427,518,0,0,0,0&220#519,520,521,522,0,0,0,0,0&221#523,519,476,509,524,427,0,0,0&222#525,524,521,526,489,0,0,0,0&223#527,521,528,0,0,0,0,0,0&224#529,530,531,532,533,471,0,0,0&225#534,465,535,0,0,0,0,0,0&226#536,537,436,538,0,0,0,0,0&227#539,476,521,472,540,0,0,0,0&228#541,542,489,0,0,0,0,0,0&229#543,541,476,0,0,0,0,0,0&230#544,470,476,0,0,0,0,0,0&231#525,476,489,0,0,0,0,0,0&232#545,546,547,0,0,0,0,0,0&233#548,426,549,436,0,0,0,0,0&234#550,530,551,427,552,0,0,0,0&235#553,554,422,555,427,556,0,0,0&236#557,558,559,560,0,0,0,0,0&237#436,561,427,562,489,0,0,0,0&238#563,564,565,530,0,0,0,0,0&239#436,566,426,448,427,0,0,0,0&240#436,567,445,460,427,568,569,0,0&241#570,571,572,573,0,0,0,0,0&242#574,480,476,575,576,0,0,0,0&243#577,476,433,489,0,0,0,0,0&244#578,444,579,0,0,0,0,0,0&245#482,530,476,470,580,0,0,0,0&246#581,582,476,583,584,585,576,532,0&247#552,429,427,550,0,0,0,0,0&248#586,587,588,560,0,0,0,0,0&249#471,589,590,560,0,0,0,0,0&250#591,462,592,520,593,594,0,0,0&251#452,595,596,460,597,0,0,0,0&252#598,599,476,572,427,0,0,0,0&253#600,601,427,602,0,0,0,0,0&254#603,604,605,606,476,607,427,433,608&255#609,610,611,612,613,592,614,597,0&256#615,525,616,0,0,0,0,0,0&257#617,603,429,476,495,427,0,0,0";
		TestBayesClassifierChatBot t = new TestBayesClassifierChatBot();
		t.learn(samplesConcatinated, "&", "#");
        t.predictQuestionAnswerId("[619, 466, 463, 462, 0, 0, 0, 0, 0]");
        //
        t.predictQuestionAnswerId("[619, 466, 463, 462]");
        //
        //String txt = "UHC|PFFS";
        //List<String> lst = new ArrayList<>(Arrays.asList(new String[]{txt}));
        //System.out.println("carrierNo:["+bayes.classify(lst, txt).getCategory()+"] for ["+txt+"]");
        //

        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        List<String> exits = new ArrayList<>(Arrays.asList(new String[] {"y","yes","Y","Yes","YES"}));
        while(exit == false) {
        	//System.out.println("Enter a sentence for classification: ");
        	System.out.println("Enter features for classification: ");
        	String question = keyboard.nextLine();
        	
            t.predictQuestionAnswerId(question);
        	
        	System.out.println("\nDo you want to exit?");
        	String s = keyboard.nextLine();
        	exit = exits.contains(s);
        }
        keyboard.close();
	}*/

	private String predictQuestionAnswerId(String txt, float probabilityThreshold) {
		List<String> splitTxt = split(txt);
		Classification<String, String> classification = bayes.classify(splitTxt, txt, probabilityThreshold);
		if (classification != null) {
			System.out.print(classification.getCategory());//do not delete this line
			return classification.getCategory();
		}
		return null;
	}
	//---------------------------
	private static final String EVERYTHING 	= "\\||\\s|\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/";
	private static final String PIPE 		= "\\|";
	private static final String NO_PIPE 	= "\\s|\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/";
	
	private List<String> split(String text) {
		Set<String> set  = new HashSet<>(Arrays.asList(text.split(EVERYTHING)));
		/*set.addAll(Arrays.asList(text.split(PIPE)));
		set.addAll(Arrays.asList(text.split(NO_PIPE)));
		set.add(text);*/
		//
		List<String> lst = new ArrayList<>(set);
		lst.removeAll(Arrays.asList("", null));
		return lst;
	}
	
	private Map<Integer, String> getMap() {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(183, "[422, 423, 0, 0, 0, 0, 0, 0, 0]");
		m.put(184, "[424, 423, 425, 0, 0, 0, 0, 0, 0]");
		m.put(185, "[426, 427, 0, 0, 0, 0, 0, 0, 0]");
		m.put(186, "[428, 429, 427, 0, 0, 0, 0, 0, 0]");
		m.put(187, "[426, 430, 427, 0, 0, 0, 0, 0, 0]");
		m.put(188, "[431, 432, 427, 0, 0, 0, 0, 0, 0]");
		m.put(189, "[433, 427, 434, 0, 0, 0, 0, 0, 0]");
		m.put(190, "[435, 0, 0, 0, 0, 0, 0, 0, 0]");
		m.put(191, "[436, 437, 438, 439, 440, 427, 441, 0, 0]");
		m.put(192, "[422, 442, 443, 0, 0, 0, 0, 0, 0]");
		m.put(193, "[427, 426, 444, 445, 0, 0, 0, 0, 0]");
		m.put(194, "[422, 427, 0, 0, 0, 0, 0, 0, 0]");
		m.put(195, "[446, 444, 445, 422, 427, 0, 0, 0, 0]");
		m.put(196, "[422, 447, 448, 427, 0, 0, 0, 0, 0]");
		m.put(197, "[422, 449, 450, 451, 0, 0, 0, 0, 0]");
		m.put(198, "[452, 453, 0, 0, 0, 0, 0, 0, 0]");
		m.put(199, "[454, 455, 456, 427, 0, 0, 0, 0, 0]");
		m.put(200, "[457, 458, 459, 427, 447, 0, 0, 0, 0]");
		m.put(201, "[422, 460, 0, 0, 0, 0, 0, 0, 0]");
		m.put(202, "[461, 462, 0, 0, 0, 0, 0, 0, 0]");
		m.put(203, "[462, 463, 464, 465, 466, 0, 0, 0, 0]");
		m.put(204, "[467, 462, 427, 0, 0, 0, 0, 0, 0]");
		m.put(205, "[468, 469, 470, 471, 472, 473, 0, 0, 0]");
		m.put(206, "[462, 474, 0, 0, 0, 0, 0, 0, 0]");
		m.put(207, "[475, 462, 476, 477, 0, 0, 0, 0, 0]");
		m.put(208, "[478, 479, 480, 481, 0, 0, 0, 0, 0]");
		m.put(209, "[482, 483, 484, 440, 485, 0, 0, 0, 0]");
		m.put(210, "[483, 486, 487, 488, 489, 0, 0, 0, 0]");
		m.put(211, "[490, 491, 476, 492, 493, 485, 0, 0, 0]");
		m.put(212, "[494, 495, 496, 0, 0, 0, 0, 0, 0]");
		m.put(213, "[497, 485, 498, 0, 0, 0, 0, 0, 0]");
		m.put(214, "[499, 500, 497, 501, 502, 503, 504, 461, 0]");
		m.put(215, "[505, 426, 506, 507, 427, 508, 0, 0, 0]");
		m.put(216, "[509, 510, 464, 511, 427, 512, 0, 0, 0]");
		m.put(217, "[427, 470, 513, 514, 0, 0, 0, 0, 0]");
		m.put(218, "[427, 515, 509, 0, 0, 0, 0, 0, 0]");
		m.put(219, "[516, 517, 509, 427, 518, 0, 0, 0, 0]");
		m.put(220, "[519, 520, 521, 522, 0, 0, 0, 0, 0]");
		m.put(221, "[523, 519, 476, 509, 524, 427, 0, 0, 0]");
		m.put(222, "[525, 524, 521, 526, 489, 0, 0, 0, 0]");
		m.put(223, "[527, 521, 528, 0, 0, 0, 0, 0, 0]");
		m.put(224, "[529, 530, 531, 532, 533, 471, 0, 0, 0]");
		m.put(225, "[534, 465, 535, 0, 0, 0, 0, 0, 0]");
		m.put(226, "[536, 537, 436, 538, 0, 0, 0, 0, 0]");
		m.put(227, "[539, 476, 521, 472, 540, 0, 0, 0, 0]");
		m.put(228, "[541, 542, 489, 0, 0, 0, 0, 0, 0]");
		m.put(229, "[543, 541, 476, 0, 0, 0, 0, 0, 0]");
		m.put(230, "[544, 470, 476, 0, 0, 0, 0, 0, 0]");
		m.put(231, "[525, 476, 489, 0, 0, 0, 0, 0, 0]");
		m.put(232, "[545, 546, 547, 0, 0, 0, 0, 0, 0]");
		m.put(233, "[548, 426, 549, 436, 0, 0, 0, 0, 0]");
		m.put(234, "[550, 530, 551, 427, 552, 0, 0, 0, 0]");
		m.put(235, "[553, 554, 422, 555, 427, 556, 0, 0, 0]");
		m.put(236, "[557, 558, 559, 560, 0, 0, 0, 0, 0]");
		m.put(237, "[436, 561, 427, 562, 489, 0, 0, 0, 0]");
		m.put(238, "[563, 564, 565, 530, 0, 0, 0, 0, 0]");
		m.put(239, "[436, 566, 426, 448, 427, 0, 0, 0, 0]");
		m.put(240, "[436, 567, 445, 460, 427, 568, 569, 0, 0]");
		m.put(241, "[570, 571, 572, 573, 0, 0, 0, 0, 0]");
		m.put(242, "[574, 480, 476, 575, 576, 0, 0, 0, 0]");
		m.put(243, "[577, 476, 433, 489, 0, 0, 0, 0, 0]");
		m.put(244, "[578, 444, 579, 0, 0, 0, 0, 0, 0]");
		m.put(245, "[482, 530, 476, 470, 580, 0, 0, 0, 0]");
		m.put(246, "[581, 582, 476, 583, 584, 585, 576, 532, 0]");
		m.put(247, "[552, 429, 427, 550, 0, 0, 0, 0, 0]");
		m.put(248, "[586, 587, 588, 560, 0, 0, 0, 0, 0]");
		m.put(249, "[471, 589, 590, 560, 0, 0, 0, 0, 0]");
		m.put(250, "[591, 462, 592, 520, 593, 594, 0, 0, 0]");
		m.put(251, "[452, 595, 596, 460, 597, 0, 0, 0, 0]");
		m.put(252, "[598, 599, 476, 572, 427, 0, 0, 0, 0]");
		m.put(253, "[600, 601, 427, 602, 0, 0, 0, 0, 0]");
		m.put(254, "[603, 604, 605, 606, 476, 607, 427, 433, 608]");
		m.put(255, "[609, 610, 611, 612, 613, 592, 614, 597, 0]");
		m.put(256, "[615, 525, 616, 0, 0, 0, 0, 0, 0]");
		m.put(257, "[617, 603, 429, 476, 495, 427, 0, 0, 0]");
		return m;
	}

}
