package com.example.goetheb1worte.data.grammar.quiz

import com.example.goetheb1worte.data.grammar.guide.CefrLevel
import com.example.goetheb1worte.data.grammar.guide.GrammarQuizType
import com.example.goetheb1worte.data.grammar.guide.InteractiveGrammarQuestion

object InteractiveGrammarQuizData {

    val allQuestions: List<InteractiveGrammarQuestion> by lazy {
        a1Questions + a2Questions + b1Questions
    }

    fun getQuestionsByLevel(level: CefrLevel): List<InteractiveGrammarQuestion> {
        return when (level) {
            CefrLevel.A1 -> a1Questions
            CefrLevel.A2 -> a2Questions
            CefrLevel.B1 -> b1Questions
        }
    }

    fun getQuestionsByTopic(topicId: String): List<InteractiveGrammarQuestion> {
        return allQuestions.filter { it.topicId == topicId }
    }

    // =========================================================================
    // LEVEL A1 INTERACTIVE EXERCISES
    // =========================================================================
    val a1Questions: List<InteractiveGrammarQuestion> = listOf(
        // --- A1.1 Pronouns & Gender ---
        InteractiveGrammarQuestion(
            id = "a1_pronoun_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_01_pronouns",
            topicTitle = "Personal Pronouns (Subject)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct pronoun replacing 'der Tisch'.",
            fullSentenceDe = "Wo ist der Tisch? – Er steht in der Küche.",
            sentenceWithBlank = "Wo ist der Tisch? – _____ steht in der Küche.",
            translationEn = "Where is the table? – It is standing in the kitchen.",
            options = listOf("Er", "Es", "Sie", "Ihn"),
            correctChoice = "Er",
            ruleExplanationDe = "💡 'der Tisch' ist maskulin. Im Deutschen wird ein maskulines Nomen immer durch das Pronomen 'er' ersetzt (nicht 'es').",
            ruleExplanationEn = "💡 'der Tisch' is masculine. In German, masculine nouns are replaced by 'er' (he/it), not 'es'.",
            tip = "Always look at the gender of the noun: der -> er, die -> sie, das -> es.",
            exceptionNote = "In English we say 'it' for all objects, but German strictly follows grammatical gender."
        ),
        InteractiveGrammarQuestion(
            id = "a1_pronoun_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_01_pronouns",
            topicTitle = "Personal Pronouns (Subject)",
            type = GrammarQuizType.CLOZE,
            prompt = "Fill in the correct subject pronoun for 1st person plural.",
            fullSentenceDe = "Wir lernen jeden Tag zusammen Deutsch.",
            sentenceWithBlank = "_____ lernen jeden Tag zusammen Deutsch.",
            translationEn = "We learn German together every day.",
            options = listOf("Wir", "Ihr", "Sie", "Ich"),
            correctChoice = "Wir",
            ruleExplanationDe = "💡 Verbendung '-en' mit Bedeutung 'wir' (1. Person Plural).",
            ruleExplanationEn = "💡 The verb ending '-en' with the collective meaning indicates 1st person plural 'Wir' (We).",
            tip = "Pronoun 'wir' takes the regular verb infinitive ending '-en'."
        ),
        InteractiveGrammarQuestion(
            id = "a1_pronoun_03",
            level = CefrLevel.A1,
            topicId = "topic_a1_01_pronouns",
            topicTitle = "Personal Pronouns (Subject)",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Arrange the words into a correct German statement.",
            fullSentenceDe = "Sie wohnen seit zwei Jahren in Berlin.",
            translationEn = "They have been living in Berlin for two years.",
            options = listOf("in Berlin.", "Sie", "wohnen", "seit zwei Jahren"),
            correctTokens = listOf("Sie", "wohnen", "seit zwei Jahren", "in Berlin."),
            ruleExplanationDe = "💡 Satzbau: Subjekt (Sie) auf Position 1, finites Verb (wohnen) auf Position 2, Zeitangabe vor Ortsangabe.",
            ruleExplanationEn = "💡 Sentence structure: Subject (Sie) in Position 1, conjugated verb (wohnen) in Position 2, time before place."
        ),

        // --- A1.2 Present Tense & Vowel Shifts ---
        InteractiveGrammarQuestion(
            id = "a1_verbs_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_02_present_verbs",
            topicTitle = "Present Tense & Vowel Changes",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct conjugated form of 'fahren' for 'du'.",
            fullSentenceDe = "Fährst du morgen mit dem Zug nach München?",
            sentenceWithBlank = "_____ du morgen mit dem Zug nach München?",
            translationEn = "Are you traveling to Munich by train tomorrow?",
            options = listOf("Fährst", "Fahrst", "Fahrt", "Fahren"),
            correctChoice = "Fährst",
            ruleExplanationDe = "💡 Das starke Verb 'fahren' hat einen Vokalwechsel von a -> ä in der 2. und 3. Person Singular: du fährst, er fährt.",
            ruleExplanationEn = "💡 The strong verb 'fahren' undergoes an a -> ä vowel shift in the 2nd and 3rd person singular: du fährst, er fährt.",
            tip = "Strong verbs with 'a' change to 'ä' only with 'du' and 'er/sie/es'.",
            exceptionNote = "'ich fahre' and 'ihr fahrt' NEVER take an umlaut!"
        ),
        InteractiveGrammarQuestion(
            id = "a1_verbs_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_02_present_verbs",
            topicTitle = "Present Tense & Vowel Changes",
            type = GrammarQuizType.CLOZE,
            prompt = "Fill in the correct form of 'sprechen' (er).",
            fullSentenceDe = "Mein Kollege spricht fließend drei Fremdsprachen.",
            sentenceWithBlank = "Mein Kollege _____ fließend drei Fremdsprachen.",
            translationEn = "My colleague speaks three foreign languages fluently.",
            options = listOf("spricht", "sprecht", "sprechen", "sprichst"),
            correctChoice = "spricht",
            ruleExplanationDe = "💡 Vokalwechsel e -> i: sprechen -> er spricht (3. Person Singular).",
            ruleExplanationEn = "💡 Vowel shift e -> i: sprechen -> er spricht (3rd person singular).",
            tip = "Verbs like helfen, sprechen, treffen change 'e' to 'i'."
        ),
        InteractiveGrammarQuestion(
            id = "a1_verbs_03",
            level = CefrLevel.A1,
            topicId = "topic_a1_02_present_verbs",
            topicTitle = "Present Tense & Vowel Changes",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Order the words with the strong verb 'sehen'.",
            fullSentenceDe = "Er sieht jeden Abend einen spannenden Film.",
            translationEn = "He watches an exciting movie every evening.",
            options = listOf("einen spannenden Film.", "sieht", "Er", "jeden Abend"),
            correctTokens = listOf("Er", "sieht", "jeden Abend", "einen spannenden Film."),
            ruleExplanationDe = "💡 'sehen' wird zu 'er sieht' (e -> ie) auf Position 2.",
            ruleExplanationEn = "💡 'sehen' conjugates to 'er sieht' (e -> ie) in Position 2."
        ),

        // --- A1.3 Verb Position 2 & Sentence Structure ---
        InteractiveGrammarQuestion(
            id = "a1_wordorder_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_03_word_order",
            topicTitle = "Verb Position 2 & Questions",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Arrange this sentence starting with the time element (Inversion).",
            fullSentenceDe = "Heute trinke ich keinen Kaffee.",
            translationEn = "Today I am not drinking any coffee.",
            options = listOf("ich", "Heute", "keinen Kaffee.", "trinke"),
            correctTokens = listOf("Heute", "trinke", "ich", "keinen Kaffee."),
            ruleExplanationDe = "💡 Inversion: Steht die Zeitangabe (Heute) auf Position 1, MUSS das Verb (trinke) auf Position 2 bleiben, gefolgt vom Subjekt (ich).",
            ruleExplanationEn = "💡 Inversion: When time (Heute) takes Position 1, the finite verb (trinke) MUST occupy Position 2, followed by the subject (ich).",
            tip = "The golden rule of German: The conjugated verb is always in Position 2 in statements!"
        ),
        InteractiveGrammarQuestion(
            id = "a1_wordorder_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_03_word_order",
            topicTitle = "Verb Position 2 & Questions",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "In a Yes/No question (Ja/Nein-Frage), what position does the verb occupy?",
            fullSentenceDe = "Kommst du heute Abend zur Party?",
            sentenceWithBlank = "_____ du heute Abend zur Party?",
            translationEn = "Are you coming to the party tonight?",
            options = listOf("Kommst", "Du kommst", "Heute kommst", "Wann kommst"),
            correctChoice = "Kommst",
            ruleExplanationDe = "💡 Ja/Nein-Fragen beginnen immer mit dem finiten Verb auf Position 1.",
            ruleExplanationEn = "💡 Yes/No questions always place the finite verb in Position 1."
        ),

        // --- A1.4 Nominativ & Akkusativ ---
        InteractiveGrammarQuestion(
            id = "a1_cases_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_04_nom_akk",
            topicTitle = "Nominativ & Akkusativ Cases",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct article for the masculine direct object (Akkusativ).",
            fullSentenceDe = "Ich kaufe morgen einen neuen Computer.",
            sentenceWithBlank = "Ich kaufe morgen _____ neuen Computer.",
            translationEn = "I am buying a new computer tomorrow.",
            options = listOf("einen", "ein", "eine", "einem"),
            correctChoice = "einen",
            ruleExplanationDe = "💡 'der Computer' ist maskulin. Als direktes Akkusativ-Objekt wird 'ein' zu 'einen'.",
            ruleExplanationEn = "💡 'der Computer' is masculine. As a direct object in Akkusativ, 'ein' becomes 'einen'.",
            tip = "Only masculine articles change in Akkusativ: der -> den, ein -> einen, kein -> keinen!"
        ),
        InteractiveGrammarQuestion(
            id = "a1_cases_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_04_nom_akk",
            topicTitle = "Nominativ & Akkusativ Cases",
            type = GrammarQuizType.CLOZE,
            prompt = "Fill in the correct definite article in Akkusativ.",
            fullSentenceDe = "Suchst du den Schlüssel für die Wohnung?",
            sentenceWithBlank = "Suchst du _____ Schlüssel für die Wohnung?",
            translationEn = "Are you looking for the key to the apartment?",
            options = listOf("den", "der", "dem", "das"),
            correctChoice = "den",
            ruleExplanationDe = "💡 'der Schlüssel' steht im Akkusativ (Wen oder was suchst du?) -> 'den Schlüssel'.",
            ruleExplanationEn = "💡 'der Schlüssel' is direct object (Akkusativ) -> 'den Schlüssel'."
        ),

        // --- A1.5 Negation: nicht vs. kein ---
        InteractiveGrammarQuestion(
            id = "a1_negation_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_05_negation",
            topicTitle = "Negation: nicht vs. kein",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "How do you negate an indefinite noun (bread)?",
            fullSentenceDe = "Wir haben heute leider kein Brot mehr.",
            sentenceWithBlank = "Wir haben heute leider _____ Brot mehr.",
            translationEn = "Unfortunately we don't have any bread left today.",
            options = listOf("kein", "nicht", "keinen", "nichts"),
            correctChoice = "kein",
            ruleExplanationDe = "💡 'das Brot' ist ein Nomen mit Nullartikel. Nomen werden mit 'kein' negiert (neutral: kein Brot).",
            ruleExplanationEn = "💡 'das Brot' is a noun with zero/indefinite article. Nouns are negated with 'kein' (neuter: kein Brot).",
            tip = "Use 'kein' for nouns with 'ein' or zero article. Use 'nicht' for verbs and adjectives!"
        ),
        InteractiveGrammarQuestion(
            id = "a1_negation_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_05_negation",
            topicTitle = "Negation: nicht vs. kein",
            type = GrammarQuizType.CLOZE,
            prompt = "Negate the adjective 'teuer'.",
            fullSentenceDe = "Dieses Hotel ist wirklich nicht teuer.",
            sentenceWithBlank = "Dieses Hotel ist wirklich _____ teuer.",
            translationEn = "This hotel is really not expensive.",
            options = listOf("nicht", "kein", "keine", "keinen"),
            correctChoice = "nicht",
            ruleExplanationDe = "💡 Adjektive ('teuer') werden immer mit 'nicht' verneint.",
            ruleExplanationEn = "💡 Adjectives ('teuer') are always negated with 'nicht'."
        ),

        // --- A1.6 Possessive Articles ---
        InteractiveGrammarQuestion(
            id = "a1_possessive_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_06_possessives",
            topicTitle = "Possessive Articles",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "What is the possessive article for 'we' with a feminine noun?",
            fullSentenceDe = "Unsere Wohnung hat drei helle Zimmer.",
            sentenceWithBlank = "_____ Wohnung hat drei helle Zimmer.",
            translationEn = "Our apartment has three bright rooms.",
            options = listOf("Unsere", "Unser", "Unseren", "Unserem"),
            correctChoice = "Unsere",
            ruleExplanationDe = "💡 'wir' -> Stamm 'unser'. Da 'die Wohnung' feminin ist, erhält der Artikel die Endung '-e' -> 'unsere'.",
            ruleExplanationEn = "💡 'wir' -> stem 'unser'. Because 'die Wohnung' is feminine, it receives the ending '-e' -> 'unsere'."
        ),

        // --- A1.8 Modal Verbs ---
        InteractiveGrammarQuestion(
            id = "a1_modals_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_08_modals",
            topicTitle = "Modal Verbs (Present Tense)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Select the correct modal form of 'können' for 'ich'.",
            fullSentenceDe = "Ich kann sehr gut Deutsch sprechen.",
            sentenceWithBlank = "Ich _____ sehr gut Deutsch sprechen.",
            translationEn = "I can speak German very well.",
            options = listOf("kann", "könne", "kannst", "könnt"),
            correctChoice = "kann",
            ruleExplanationDe = "💡 Modalverben im Singular: 'ich' und 'er/sie/es' haben KEINE Endung: ich kann, er kann.",
            ruleExplanationEn = "💡 Modal verbs in singular: 1st person ('ich') and 3rd person ('er/sie/es') take NO ENDING: ich kann, er kann.",
            tip = "1st and 3rd person singular of all modal verbs are identical and end without -e or -t!"
        ),
        InteractiveGrammarQuestion(
            id = "a1_modals_02",
            level = CefrLevel.A1,
            topicId = "topic_a1_08_modals",
            topicTitle = "Modal Verbs (Present Tense)",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Order the sentence with a modal verb and infinitive bracket.",
            fullSentenceDe = "Wir müssen heute Abend die Hausaufgaben machen.",
            translationEn = "We have to do the homework tonight.",
            options = listOf("die Hausaufgaben machen.", "müssen", "Wir", "heute Abend"),
            correctTokens = listOf("Wir", "müssen", "heute Abend", "die Hausaufgaben machen."),
            ruleExplanationDe = "💡 Satzklammer: Modalverb (müssen) auf Position 2, Hauptverb im Infinitiv (machen) ganz am Satzende.",
            ruleExplanationEn = "💡 Sentence bracket: Modal verb (müssen) in Position 2, main verb in base infinitive (machen) at the very end."
        ),

        // --- A1.10 Separable Verbs ---
        InteractiveGrammarQuestion(
            id = "a1_sep_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_10_separable_verbs",
            topicTitle = "Separable Verbs",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Build the sentence with separable verb 'aufstehen'.",
            fullSentenceDe = "Er steht jeden Tag um sechs Uhr auf.",
            translationEn = "He gets up every day at six o'clock.",
            options = listOf("steht", "auf.", "Er", "jeden Tag um sechs Uhr"),
            correctTokens = listOf("Er", "steht", "jeden Tag um sechs Uhr", "auf."),
            ruleExplanationDe = "💡 Trennbares Verb: Konjugierter Verbstamm (steht) auf Position 2, abgetrenntes Präfix (auf) am Ende.",
            ruleExplanationEn = "💡 Separable verb: Conjugated verb stem (steht) in Position 2, detached prefix (auf) at the end."
        ),

        // --- A1.11 Perfekt Tense ---
        InteractiveGrammarQuestion(
            id = "a1_perfekt_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_11_perfekt_intro",
            topicTitle = "Perfekt Tense (Introduction)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct auxiliary for movement 'nach Hause gehen'.",
            fullSentenceDe = "Gestern bin ich sehr spät nach Hause gegangen.",
            sentenceWithBlank = "Gestern _____ ich sehr spät nach Hause gegangen.",
            translationEn = "Yesterday I went home very late.",
            options = listOf("bin", "habe", "war", "hatte"),
            correctChoice = "bin",
            ruleExplanationDe = "💡 'gehen' ist ein Verb der Ortsveränderung und bildet das Perfekt immer mit 'sein': ich bin gegangen.",
            ruleExplanationEn = "💡 'gehen' indicates movement from point A to B and always forms Perfekt with 'sein': ich bin gegangen.",
            tip = "Movement (gehen, fahren, fliegen, kommen) and change of state (aufwachen) take 'sein'!"
        ),

        // --- A1.12 Prepositions ---
        InteractiveGrammarQuestion(
            id = "a1_prep_01",
            level = CefrLevel.A1,
            topicId = "topic_a1_12_basic_prepositions",
            topicTitle = "Basic Prepositions",
            type = GrammarQuizType.CLOZE,
            prompt = "Which preposition is used for days of the week?",
            fullSentenceDe = "Am Samstag treffen wir uns im Café.",
            sentenceWithBlank = "_____ Samstag treffen wir uns im Café.",
            translationEn = "On Saturday we are meeting at the café.",
            options = listOf("Am", "Um", "Im", "In"),
            correctChoice = "Am",
            ruleExplanationDe = "💡 Wochentage und Tageszeiten nehmen die Präposition 'am' (an + dem): am Montag, am Samstag, am Morgen.",
            ruleExplanationEn = "💡 Days of the week and times of day take 'am' (an + dem): am Montag, am Samstag, am Morgen.",
            tip = "am = days & dates; um = clock times; im = months & seasons!"
        )
    )

    // =========================================================================
    // LEVEL A2 INTERACTIVE EXERCISES
    // =========================================================================
    val a2Questions: List<InteractiveGrammarQuestion> = listOf(
        // --- A2.1 Dativ Case & Verbs ---
        InteractiveGrammarQuestion(
            id = "a2_dativ_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_01_dativ_case",
            topicTitle = "Dativ Case & Dative Verbs",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct pronoun for the dative verb 'helfen'.",
            fullSentenceDe = "Kannst du mir bitte kurz helfen?",
            sentenceWithBlank = "Kannst du _____ bitte kurz helfen?",
            translationEn = "Can you please help me for a moment?",
            options = listOf("mir", "mich", "mein", "ich"),
            correctChoice = "mir",
            ruleExplanationDe = "💡 'helfen' verlangt zwingend den Dativ: 'du hilfst mir' (nicht mich).",
            ruleExplanationEn = "💡 'helfen' strictly governs the Dativ case: 'du hilfst mir' (not mich).",
            tip = "Core Dativ verbs: helfen, danken, gefallen, gehören, schmecken, passen!"
        ),
        InteractiveGrammarQuestion(
            id = "a2_dativ_02",
            level = CefrLevel.A2,
            topicId = "topic_a2_01_dativ_case",
            topicTitle = "Dativ Case & Dative Verbs",
            type = GrammarQuizType.CLOZE,
            prompt = "What is the masculine definite article in Dativ?",
            fullSentenceDe = "Ich antworte dem Chef noch heute.",
            sentenceWithBlank = "Ich antworte _____ Chef noch heute.",
            translationEn = "I will answer the boss today.",
            options = listOf("dem", "den", "der", "des"),
            correctChoice = "dem",
            ruleExplanationDe = "💡 'antworten' verlangt Dativ. Der maskuline Dativartikel ist 'dem'.",
            ruleExplanationEn = "💡 'antworten' governs Dativ. The masculine Dativ article is 'dem'."
        ),

        // --- A2.2 Two-Way Prepositions ---
        InteractiveGrammarQuestion(
            id = "a2_wechsel_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_02_two_way_preps",
            topicTitle = "Two-Way Prepositions (Wo vs. Wohin)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Stationary location (Wo? / Where?) takes Dativ. Choose the article for 'der Tisch'.",
            fullSentenceDe = "Das Buch liegt auf dem Tisch.",
            sentenceWithBlank = "Das Buch liegt auf _____ Tisch.",
            translationEn = "The book is lying on the table.",
            options = listOf("dem", "den", "der", "das"),
            correctChoice = "dem",
            ruleExplanationDe = "💡 'liegen' beschreibt eine statische Position (Wo? -> Dativ). Maskulin Dativ: 'auf dem Tisch'.",
            ruleExplanationEn = "💡 'liegen' describes a static position (Wo? -> Dativ). Masculine Dativ: 'auf dem Tisch'.",
            tip = "liegen / stehen / sitzen = Dativ (Wo?). legen / stellen / setzen = Akkusativ (Wohin?)."
        ),
        InteractiveGrammarQuestion(
            id = "a2_wechsel_02",
            level = CefrLevel.A2,
            topicId = "topic_a2_02_two_way_preps",
            topicTitle = "Two-Way Prepositions (Wo vs. Wohin)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Direction / destination (Wohin? / Where to?) takes Akkusativ. Choose the article.",
            fullSentenceDe = "Ich lege das Buch auf den Tisch.",
            sentenceWithBlank = "Ich lege das Buch auf _____ Tisch.",
            translationEn = "I put the book on the table.",
            options = listOf("den", "dem", "der", "das"),
            correctChoice = "den",
            ruleExplanationDe = "💡 'legen' beschreibt eine Bewegung/Richtung (Wohin? -> Akkusativ). Maskulin Akkusativ: 'auf den Tisch'.",
            ruleExplanationEn = "💡 'legen' describes motion/direction (Wohin? -> Akkusativ). Masculine Akkusativ: 'auf den Tisch'."
        ),

        // --- A2.3 Strict Dativ Prepositions ---
        InteractiveGrammarQuestion(
            id = "a2_datprep_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_03_dativ_preps",
            topicTitle = "Strict Dative Prepositions",
            type = GrammarQuizType.CLOZE,
            prompt = "Fill in the correct article after 'mit' (feminine: die U-Bahn).",
            fullSentenceDe = "Ich fahre jeden Tag mit der U-Bahn zur Arbeit.",
            sentenceWithBlank = "Ich fahre jeden Tag mit _____ U-Bahn zur Arbeit.",
            translationEn = "I ride to work every day by subway.",
            options = listOf("der", "die", "dem", "den"),
            correctChoice = "der",
            ruleExplanationDe = "💡 'mit' verlangt IMMER den Dativ. Feminin Dativ: 'mit der U-Bahn'.",
            ruleExplanationEn = "💡 'mit' ALWAYS governs Dativ. Feminine Dativ: 'mit der U-Bahn'.",
            tip = "Always remember: aus, bei, mit, nach, seit, von, zu -> 100% Dativ!"
        ),

        // --- A2.5 Präteritum of Modals & sein/haben ---
        InteractiveGrammarQuestion(
            id = "a2_praet_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_05_praeteritum_modals",
            topicTitle = "Präteritum of sein, haben & Modals",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the past tense (Präteritum) of 'können' for 'ich'.",
            fullSentenceDe = "Gestern konnte ich leider nicht zum Deutschkurs kommen.",
            sentenceWithBlank = "Gestern _____ ich leider nicht zum Deutschkurs kommen.",
            translationEn = "Yesterday I unfortunately could not come to the German class.",
            options = listOf("konnte", "könnte", "kann", "gekonnt"),
            correctChoice = "konnte",
            ruleExplanationDe = "💡 Präteritum von 'können' verliert den Umlaut: 'ich konnte'. ('könnte' ist Konjunktiv II!).",
            ruleExplanationEn = "💡 Präteritum of 'können' drops the umlaut: 'ich konnte'. ('könnte' is Konjunktiv II!).",
            exceptionNote = "Never confuse past tense 'musste/konnte' with polite 'müsste/könnte'."
        ),

        // --- A2.6 Comparative & Superlative ---
        InteractiveGrammarQuestion(
            id = "a2_comp_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_06_comparative",
            topicTitle = "Comparative & Superlative",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the irregular comparative of 'gut'.",
            fullSentenceDe = "Dieser Kaffee schmeckt viel besser als der andere.",
            sentenceWithBlank = "Dieser Kaffee schmeckt viel _____ als der andere.",
            translationEn = "This coffee tastes much better than the other one.",
            options = listOf("besser", "guter", "am besten", "mehr gut"),
            correctChoice = "besser",
            ruleExplanationDe = "💡 'gut' ist unregelmäßig: gut -> besser -> am besten. Bei Unterschieden steht immer 'als'.",
            ruleExplanationEn = "💡 'gut' is irregular: gut -> besser -> am besten. Differences always use 'als'.",
            tip = "Equality: so ... wie. Inequality: Komparativ + als!"
        ),

        // --- A2.7 Subordinate Clauses (weil, dass, wenn) ---
        InteractiveGrammarQuestion(
            id = "a2_subord_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_07_subordinate_clauses",
            topicTitle = "Subordinate Clauses (weil, dass, wenn)",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Assemble the subordinate clause with 'weil' (verb-final rule).",
            fullSentenceDe = "Ich bleibe zu Hause, weil ich krank bin.",
            translationEn = "I am staying home because I am sick.",
            options = listOf("weil", "Ich bleibe zu Hause,", "bin.", "ich krank"),
            correctTokens = listOf("Ich bleibe zu Hause,", "weil", "ich krank", "bin."),
            ruleExplanationDe = "💡 Nebensatzregel: Konjunktion 'weil' schickt das konjugierte Verb 'bin' an das absolute Satzende.",
            ruleExplanationEn = "💡 Subordinate clause rule: Conjunction 'weil' kicks the finite verb 'bin' to the very end of the clause."
        ),
        InteractiveGrammarQuestion(
            id = "a2_subord_02",
            level = CefrLevel.A2,
            topicId = "topic_a2_07_subordinate_clauses",
            topicTitle = "Subordinate Clauses (weil, dass, wenn)",
            type = GrammarQuizType.CLOZE,
            prompt = "Fill in the correct conjunction for a fact/statement ('that').",
            fullSentenceDe = "Ich weiß, dass Deutsch lernen viel Spaß macht.",
            sentenceWithBlank = "Ich weiß, _____ Deutsch lernen viel Spaß macht.",
            translationEn = "I know that learning German is a lot of fun.",
            options = listOf("dass", "weil", "wenn", "ob"),
            correctChoice = "dass",
            ruleExplanationDe = "💡 'dass' leitet einen Objektsatz ein und schickt das Verb 'macht' ans Satzende.",
            ruleExplanationEn = "💡 'dass' (that) introduces an object clause with verb-final word order."
        ),

        // --- A2.8 Infinitive with "zu" ---
        InteractiveGrammarQuestion(
            id = "a2_infzu_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_08_infinitive_zu",
            topicTitle = "Infinitive with 'zu'",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "How does a separable verb incorporate 'zu'?",
            fullSentenceDe = "Ich habe keine Lust, heute so früh aufzustehen.",
            sentenceWithBlank = "Ich habe keine Lust, heute so früh _____.",
            translationEn = "I have no desire to get up so early today.",
            options = listOf("aufzustehen", "zu aufstehen", "aufstehen zu", "aufstehen"),
            correctChoice = "aufzustehen",
            ruleExplanationDe = "💡 Bei trennbaren Verben wird 'zu' zwischen Präfix und Stamm eingefügt: auf-zu-stehen.",
            ruleExplanationEn = "💡 With separable verbs, 'zu' is inserted between the prefix and the verb stem: auf-zu-stehen.",
            tip = "Prefix + zu + Verb: einkaufen -> einzukaufen, anrufen -> anzurufen."
        ),

        // --- A2.10 Adjective Declension Intro ---
        InteractiveGrammarQuestion(
            id = "a2_adj_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_10_adjective_declension",
            topicTitle = "Adjective Declension (Nom & Akk)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct ending after 'der' in Nominativ singular.",
            fullSentenceDe = "Der neue Lehrer erklärt alles sehr verständlich.",
            sentenceWithBlank = "Der _____ Lehrer erklärt alles sehr verständlich.",
            translationEn = "The new teacher explains everything very clearly.",
            options = listOf("neue", "neuer", "neuen", "neues"),
            correctChoice = "neue",
            ruleExplanationDe = "💡 Schwache Adjektivdeklination: Nach 'der' (maskulin Nominativ) nimmt das Adjektiv die Endung '-e'.",
            ruleExplanationEn = "💡 Weak adjective declension: After definite article 'der' (masculine Nominativ), the adjective takes '-e'."
        ),

        // --- A2.12 Konjunktiv II Polite Requests ---
        InteractiveGrammarQuestion(
            id = "a2_konj2_01",
            level = CefrLevel.A2,
            topicId = "topic_a2_12_konjunktiv2_intro",
            topicTitle = "Konjunktiv II: Polite Requests",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Select the polite subjunctive form of 'können' for 'Sie'.",
            fullSentenceDe = "Könnten Sie mir bitte die Rechnung bringen?",
            sentenceWithBlank = "_____ Sie mir bitte die Rechnung bringen?",
            translationEn = "Could you please bring me the bill?",
            options = listOf("Könnten", "Konnten", "Kann", "Können"),
            correctChoice = "Könnten",
            ruleExplanationDe = "💡 'Könnten Sie...' ist die höfliche Bitte im Konjunktiv II (mit Umlaut).",
            ruleExplanationEn = "💡 'Könnten Sie...' is the polite request formula in Konjunktiv II (with umlaut)."
        )
    )

    // =========================================================================
    // LEVEL B1 INTERACTIVE EXERCISES
    // =========================================================================
    val b1Questions: List<InteractiveGrammarQuestion> = listOf(
        // --- B1.1 Genitiv & Prepositions ---
        InteractiveGrammarQuestion(
            id = "b1_genitiv_01",
            level = CefrLevel.B1,
            topicId = "topic_01_cases",
            topicTitle = "Cases & Noun Declension",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Choose the correct article for the Genitiv preposition 'wegen' with a masculine noun.",
            fullSentenceDe = "Wegen des starken Regens wurde das Fußballspiel abgesagt.",
            sentenceWithBlank = "Wegen _____ starken Regens wurde das Fußballspiel abgesagt.",
            translationEn = "Because of the heavy rain, the soccer match was canceled.",
            options = listOf("des", "dem", "den", "der"),
            correctChoice = "des",
            ruleExplanationDe = "💡 'wegen' verlangt im B1-Standarddeutsch den Genitiv. Maskulin: 'des starken Regens' (+s am Nomen).",
            ruleExplanationEn = "💡 'wegen' requires Genitiv in standard B1 German. Masculine: 'des starken Regens' (+s on the noun).",
            tip = "Genitiv prepositions: wegen, während, trotz, anstatt + des/der/des/der!",
            exceptionNote = "In spoken German Dativ is often heard, but on the Goethe B1 exam Genitiv is strictly required!"
        ),
        InteractiveGrammarQuestion(
            id = "b1_genitiv_02",
            level = CefrLevel.B1,
            topicId = "topic_01_cases",
            topicTitle = "Cases & Noun Declension",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Weak masculine noun (N-Deklination) in Dativ: Select the correct form.",
            fullSentenceDe = "Ich habe dem neuen Kunden die Dokumente geschickt.",
            sentenceWithBlank = "Ich habe dem neuen _____ die Dokumente geschickt.",
            translationEn = "I sent the documents to the new client.",
            options = listOf("Kunden", "Kunde", "Kundes", "Kundem"),
            correctChoice = "Kunden",
            ruleExplanationDe = "💡 N-Deklination: 'der Kunde' ist ein schwaches Maskulinum und erhält im Akk, Dat und Gen immer die Endung '-n': 'dem Kunden'.",
            ruleExplanationEn = "💡 N-Deklination: 'der Kunde' is a weak masculine noun and takes the ending '-n' in all cases except Nominativ singular: 'dem Kunden'.",
            tip = "N-Deklination nouns (Junge, Kollege, Kunde, Nachbar, Student) take -(e)n in Akk, Dat, Gen!"
        ),

        // --- B1.2 Verbs with Fixed Prepositions & Da-compounds ---
        InteractiveGrammarQuestion(
            id = "b1_preps_01",
            level = CefrLevel.B1,
            topicId = "topic_02_verbs_prepositions",
            topicTitle = "Verbs & Fixed Prepositions",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Which preposition and case does 'warten' govern?",
            fullSentenceDe = "Wir warten schon seit einer halben Stunde auf den Bus.",
            sentenceWithBlank = "Wir warten schon seit einer halben Stunde _____ den Bus.",
            translationEn = "We have been waiting for the bus for half an hour.",
            options = listOf("auf", "für", "an", "nach"),
            correctChoice = "auf",
            ruleExplanationDe = "💡 'warten auf' verlangt immer den Akkusativ: 'auf den Bus'.",
            ruleExplanationEn = "💡 'warten auf' strictly takes the Akkusativ case: 'auf den Bus'."
        ),
        InteractiveGrammarQuestion(
            id = "b1_preps_02",
            level = CefrLevel.B1,
            topicId = "topic_02_verbs_prepositions",
            topicTitle = "Verbs & Fixed Prepositions",
            type = GrammarQuizType.CLOZE,
            prompt = "Choose the correct Pronominaladverb (Da-compound) referring to an upcoming vacation.",
            fullSentenceDe = "Ich freue mich schon riesig darauf.",
            sentenceWithBlank = "Ich freue mich schon riesig _____.",
            translationEn = "I am already looking forward to it enormously.",
            options = listOf("darauf", "worauf", "auf es", "daran"),
            correctChoice = "darauf",
            ruleExplanationDe = "💡 Pronominaladverb: da + r + auf -> 'darauf' (für Sachen/Ereignisse). Niemals 'auf es' sagen!",
            ruleExplanationEn = "💡 Pronominal adverb: da + r + auf -> 'darauf' (used for things/events). Never say 'auf es'!"
        ),

        // --- B1.4 Verb Tenses & Passive Voice ---
        InteractiveGrammarQuestion(
            id = "b1_passive_01",
            level = CefrLevel.B1,
            topicId = "topic_05_passive",
            topicTitle = "Passive Voice (Vorgangspassiv)",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Form the present tense passive voice (Passiv Präsens).",
            fullSentenceDe = "In Deutschland wird viel Kaffee getrunken.",
            sentenceWithBlank = "In Deutschland _____ viel Kaffee getrunken.",
            translationEn = "A lot of coffee is drunk in Germany.",
            options = listOf("wird", "wurde", "worden", "hat"),
            correctChoice = "wird",
            ruleExplanationDe = "💡 Passiv Präsens wird gebildet mit: 'werden' (konjugiert) + Partizip II (getrunken).",
            ruleExplanationEn = "💡 Present passive is formed with: 'werden' (conjugated) + Partizip II (getrunken).",
            tip = "Passiv Präteritum uses 'wurde'; Passiv Präsens uses 'wird'!"
        ),
        InteractiveGrammarQuestion(
            id = "b1_passive_02",
            level = CefrLevel.B1,
            topicId = "topic_05_passive",
            topicTitle = "Passive Voice (Vorgangspassiv)",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Build the passive sentence with modal verb 'müssen'.",
            fullSentenceDe = "Dieser Fehler muss sofort korrigiert werden.",
            translationEn = "This error must be corrected immediately.",
            options = listOf("muss", "korrigiert werden.", "Dieser Fehler", "sofort"),
            correctTokens = listOf("Dieser Fehler", "muss", "sofort", "korrigiert werden."),
            ruleExplanationDe = "💡 Passiv mit Modalverb: Modalverb auf Position 2, Partizip II + 'werden' am Satzende.",
            ruleExplanationEn = "💡 Passive with modal verb: Modal verb in Position 2, Partizip II + 'werden' at the clause end."
        ),

        // --- B1.6 Konjunktiv II (Unreal & Advice) ---
        InteractiveGrammarQuestion(
            id = "b1_konj2_01",
            level = CefrLevel.B1,
            topicId = "topic_06_konjunktiv2",
            topicTitle = "Konjunktiv II",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Select the correct form to give advice ('should').",
            fullSentenceDe = "Du bist sehr müde, du solltest früher schlafen gehen.",
            sentenceWithBlank = "Du bist sehr müde, du _____ früher schlafen gehen.",
            translationEn = "You are very tired; you should go to sleep earlier.",
            options = listOf("solltest", "sollst", "würdest", "hättest"),
            correctChoice = "solltest",
            ruleExplanationDe = "💡 'solltest' (Konjunktiv II von sollen) drückt im Deutschen Ratschläge und Empfehlungen aus.",
            ruleExplanationEn = "💡 'solltest' (Konjunktiv II of sollen) expresses advice and recommendations in German."
        ),
        InteractiveGrammarQuestion(
            id = "b1_konj2_02",
            level = CefrLevel.B1,
            topicId = "topic_06_konjunktiv2",
            topicTitle = "Konjunktiv II",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Assemble the unreal conditional clause (Wenn-Satz).",
            fullSentenceDe = "Wenn ich reich wäre, würde ich eine Weltreise machen.",
            translationEn = "If I were rich, I would take a trip around the world.",
            options = listOf("würde ich", "eine Weltreise machen.", "Wenn ich reich wäre,"),
            correctTokens = listOf("Wenn ich reich wäre,", "würde ich", "eine Weltreise machen."),
            ruleExplanationDe = "💡 Irrealer Konditionalsatz: Nebensatz mit 'wäre' am Ende -> Hauptsatz beginnt direkt mit 'würde ich'.",
            ruleExplanationEn = "💡 Unreal conditional: Subordinate clause ends with 'wäre' -> main clause starts immediately with 'würde ich'."
        ),

        // --- B1.7 Two-Part Connectors (Doppelkonnektoren) ---
        InteractiveGrammarQuestion(
            id = "b1_conn_01",
            level = CefrLevel.B1,
            topicId = "topic_07_sentence_structure",
            topicTitle = "Sentence Structure & Word Order",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Complete the two-part connector: 'nicht nur ..., sondern ...'",
            fullSentenceDe = "Er spricht nicht nur Deutsch, sondern auch perfekt Französisch.",
            sentenceWithBlank = "Er spricht nicht nur Deutsch, _____ auch perfekt Französisch.",
            translationEn = "He speaks not only German, but also French perfectly.",
            options = listOf("sondern", "aber", "oder", "und"),
            correctChoice = "sondern",
            ruleExplanationDe = "💡 Fester Doppelkonnektor: 'nicht nur ..., sondern auch ...' (Addition/Ergänzung).",
            ruleExplanationEn = "💡 Fixed two-part connector: 'nicht nur ..., sondern auch ...' (not only ..., but also ...).",
            tip = "Other key pairs: sowohl ... als auch, entweder ... oder, weder ... noch!"
        ),
        InteractiveGrammarQuestion(
            id = "b1_conn_02",
            level = CefrLevel.B1,
            topicId = "topic_07_sentence_structure",
            topicTitle = "Sentence Structure & Word Order",
            type = GrammarQuizType.WORD_ORDER,
            prompt = "Order the proportional clause: 'je ... desto ...'",
            fullSentenceDe = "Je mehr du liest, desto besser sprichst du.",
            translationEn = "The more you read, the better you speak.",
            options = listOf("desto besser", "Je mehr", "du liest,", "sprichst du."),
            correctTokens = listOf("Je mehr", "du liest,", "desto besser", "sprichst du."),
            ruleExplanationDe = "💡 'je' leitet einen Nebensatz ein (Verb liest am Ende), 'desto + Komparativ' steht auf Position 1 (Verb sprichst auf Position 2).",
            ruleExplanationEn = "💡 'je' introduces a subordinate clause (verb liest at end), 'desto + comparative' is Position 1 (verb sprichst in Position 2)."
        ),

        // --- B1.8 Infinitive & Purpose Clauses (um...zu vs. damit) ---
        InteractiveGrammarQuestion(
            id = "b1_purpose_01",
            level = CefrLevel.B1,
            topicId = "topic_08_infinitive_clauses",
            topicTitle = "Infinitive & Purpose Clauses",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Different subjects in purpose clause: The teacher speaks slowly SO THAT the students understand.",
            fullSentenceDe = "Die Lehrerin spricht langsam, damit die Schüler alles verstehen.",
            sentenceWithBlank = "Die Lehrerin spricht langsam, _____ die Schüler alles verstehen.",
            translationEn = "The teacher speaks slowly so that the students understand everything.",
            options = listOf("damit", "um ... zu", "weil", "obwohl"),
            correctChoice = "damit",
            ruleExplanationDe = "💡 Unterschiedliche Subjekte (Lehrerin vs. Schüler) verlangen zwingend 'damit' mit Verb am Ende ('um ... zu' geht nur bei gleichem Subjekt!).",
            ruleExplanationEn = "💡 Different subjects (teacher vs. students) strictly require 'damit' with verb at the end ('um ... zu' is only possible when subjects are identical!).",
            exceptionNote = "'um ... zu' is forbidden if the main and subordinate clauses have different subjects."
        ),

        // --- B1.9 Relative Clauses with Prepositions & dessen/deren ---
        InteractiveGrammarQuestion(
            id = "b1_relative_01",
            level = CefrLevel.B1,
            topicId = "topic_09_relative_clauses",
            topicTitle = "Relative Clauses",
            type = GrammarQuizType.SINGLE_CHOICE,
            prompt = "Possessive relative pronoun for masculine noun 'der Kollege': 'The colleague whose car...'",
            fullSentenceDe = "Das ist der Kollege, dessen Auto gestern beschädigt wurde.",
            sentenceWithBlank = "Das ist der Kollege, _____ Auto gestern beschädigt wurde.",
            translationEn = "That is the colleague whose car was damaged yesterday.",
            options = listOf("dessen", "deren", "dem", "den"),
            correctChoice = "dessen",
            ruleExplanationDe = "💡 Genitiv-Relativpronomen für Maskulinum/Neutrum: 'dessen' (wessen Auto? -> dessen Auto).",
            ruleExplanationEn = "💡 Genitive relative pronoun for masculine/neuter: 'dessen' (whose car? -> dessen Auto).",
            tip = "dessen = masculine/neuter; deren = feminine/plural!"
        )
    )
}
