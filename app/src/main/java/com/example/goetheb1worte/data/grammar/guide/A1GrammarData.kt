package com.example.goetheb1worte.data.grammar.guide

import com.example.goetheb1worte.data.repository.GameType

object A1GrammarData {

    val topics: List<GrammarGuideTopic> = listOf(
        // ---------------------------------------------------------------------
        // A1.1: PERSONAL PRONOUNS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_01_pronouns",
            number = 1,
            level = CefrLevel.A1,
            title = "Personal Pronouns (Subject)",
            germanTitle = "Personalpronomen (Nominativ)",
            subtitle = "ich, du, er/sie/es, wir, ihr, sie/Sie",
            category = "Pronouns",
            iconEmoji = "👤",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Personal pronouns replace nouns and act as the subject (Nominativ) of a sentence. In German, grammatical gender replaces things (der Tisch -> er, die Lampe -> sie, das Buch -> es).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "sie vs. Sie: 'sie' (lowercase) means 'she' or 'they'; 'Sie' (always capitalized) is the formal address for 'you' (singular and plural).",
                "Grammatical gender of objects: German replaces objects by their grammatical article: 'Wo ist der Schlüssel? – Er liegt hier.' (not 'es').",
                "ihr vs. er: 'ihr' is plural informal ('you guys'), 'er' is 3rd person singular ('he')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_01_pronouns_table",
                    title = "Subject Pronouns Table",
                    explanation = "Every sentence needs a subject. The conjugated verb agrees directly with the subject pronoun.",
                    table = GrammarTable(
                        headers = listOf("Person", "Pronoun", "English", "Example"),
                        rows = listOf(
                            listOf("1st Sing.", "ich", "I", "Ich wohne in Berlin."),
                            listOf("2nd Sing. (informal)", "du", "you", "Woher kommst du?"),
                            listOf("3rd Sing. (masculine)", "er", "he / it", "Er heißt Thomas."),
                            listOf("3rd Sing. (feminine)", "sie", "she / it", "Sie lernt Deutsch."),
                            listOf("3rd Sing. (neuter)", "es", "it", "Es ist kalt heute."),
                            listOf("1st Plur.", "wir", "we", "Wir lernen zusammen."),
                            listOf("2nd Plur. (informal)", "ihr", "you all", "Seid ihr bereit?"),
                            listOf("3rd Plur.", "sie", "they", "Sie sprechen Spanisch."),
                            listOf("Formal (sing./plur.)", "Sie", "you (formal)", "Wo wohnen Sie?")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich lerne seit drei Monaten Deutsch.",
                            highlightWord = "Ich",
                            english = "I have been learning German for three months.",
                            note = "1st person singular pronoun 'ich' with regular verb ending '-e'."
                        ),
                        GrammarExample(
                            german = "Wo ist meine Tasche? – Sie liegt auf dem Tisch.",
                            highlightWord = "Sie",
                            english = "Where is my bag? – It is lying on the table.",
                            note = "'die Tasche' is feminine, so it is replaced by 'sie' (it)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.2: PRESENT TENSE VERB CONJUGATION
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_02_present_verbs",
            number = 2,
            level = CefrLevel.A1,
            title = "Present Tense & Vowel Changes",
            germanTitle = "Präsens & Vokalwechsel",
            subtitle = "Regular Endings, sein/haben & Strong Verbs",
            category = "Verbs",
            iconEmoji = "🔄",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "In German, verbs take endings depending on the subject (-e, -st, -t, -en, -t, -en). Irregular strong verbs change their stem vowel in the 2nd (du) and 3rd (er/sie/es) person singular (e -> i/ie, a -> ä).",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Vowel changes happen ONLY for 'du' and 'er/sie/es', never for 'ich', 'wir', 'ihr', or 'sie/Sie' (e.g., ich fahre, du fährst, wir fahren).",
                "haben and sein are completely irregular: du hast / er hat (no -b-), and ich bin / du bist / er ist / wir sind / ihr seid / sie sind.",
                "Verbs ending in -t or -d (arbeiten, finden) insert an extra -e- before endings (du arbeitest, er arbeitet)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_02_regular_endings",
                    title = "Regular Verb Endings & sein/haben",
                    explanation = "Remove the infinitive ending -en to get the verb stem, then attach the person ending: -e, -st, -t, -en, -t, -en.",
                    table = GrammarTable(
                        headers = listOf("Person", "lernen (regular)", "haben", "sein"),
                        rows = listOf(
                            listOf("ich", "lern-e", "habe", "bin"),
                            listOf("du", "lern-st", "hast", "bist"),
                            listOf("er/sie/es", "lern-t", "hat", "ist"),
                            listOf("wir", "lern-en", "haben", "sind"),
                            listOf("ihr", "lern-t", "habt", "seid"),
                            listOf("sie/Sie", "lern-en", "haben", "sind")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Er arbeitet bei einer großen Firma in Frankfurt.",
                            highlightWord = "arbeitet",
                            english = "He works at a large company in Frankfurt.",
                            note = "Verb stem 'arbeit-' inserts '-e-' before '-t'."
                        ),
                        GrammarExample(
                            german = "Wir sind heute sehr müde.",
                            highlightWord = "sind",
                            english = "We are very tired today.",
                            note = "Irregular verb 'sein' (wir sind)."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_a1_02_vowel_shifts",
                    title = "Strong Verbs with Vowel Shift (e -> i/ie, a -> ä)",
                    explanation = "Many essential German verbs change their stem vowel in 'du' and 'er/sie/es': e -> i (sprechen -> spricht), e -> ie (lesen -> liest, sehen -> sieht), a -> ä (fahren -> fährt, schlafen -> schläft).",
                    table = GrammarTable(
                        headers = listOf("Infinitive", "ich", "du", "er/sie/es", "Shift Type"),
                        rows = listOf(
                            listOf("sprechen", "spreche", "sprichst", "spricht", "e -> i"),
                            listOf("essen", "esse", "isst", "isst", "e -> i"),
                            listOf("sehen", "sehe", "siehst", "sieht", "e -> ie"),
                            listOf("lesen", "lese", "liest", "liest", "e -> ie"),
                            listOf("fahren", "fahre", "fährst", "fährt", "a -> ä"),
                            listOf("schlafen", "schlafe", "schläfst", "schläft", "a -> ä")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Er spricht sehr gut Deutsch und Englisch.",
                            highlightWord = "spricht",
                            english = "He speaks German and English very well.",
                            note = "Vowel change e -> i in 3rd person singular (sprechen -> spricht)."
                        ),
                        GrammarExample(
                            german = "Fährst du morgen mit dem Zug nach München?",
                            highlightWord = "Fährst",
                            english = "Are you traveling by train to Munich tomorrow?",
                            note = "Vowel change a -> ä in 2nd person singular (fahren -> fährst)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.3: WORD ORDER & QUESTION FORMATION
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_03_word_order",
            number = 3,
            level = CefrLevel.A1,
            title = "Verb Position 2 & Questions",
            germanTitle = "Satzbau: Verb auf Position 2 & Fragen",
            subtitle = "Main Clauses, W-Questions & Yes/No Questions",
            category = "Sentence Structure",
            iconEmoji = "📐",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The golden rule of German statements: the conjugated finite verb is ALWAYS in Position 2. In Yes/No questions, the verb jumps to Position 1. In W-questions, the question word is in Position 1 and the verb is in Position 2.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Position 1 can be a time or location expression (e.g. 'Heute', 'In Berlin'). When Position 1 is filled, the subject must invert to Position 3: 'Heute lerne ich Deutsch' (NOT: 'Heute ich lerne').",
                "W-Fragen require the verb immediately in Position 2: 'Wo wohnst du?' (NOT: 'Wo du wohnst?').",
                "Ja/Nein-Fragen start directly with the conjugated verb: 'Kommst du morgen?' (NOT: 'Du kommst morgen?')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_03_v2_rule",
                    title = "The Golden Rule: Verb in Position 2",
                    explanation = "In normal main clauses (Hauptsätze), the conjugated verb is strictly in the second syntactic position. Whatever element starts the sentence (Subject, Time, Place), the verb stays at #2.",
                    table = GrammarTable(
                        headers = listOf("Position 1", "Position 2 (Verb)", "Position 3 (Subject/Other)", "Rest of Sentence"),
                        rows = listOf(
                            listOf("Ich (Subject)", "lerne", "heute", "Deutsch."),
                            listOf("Heute (Time)", "lerne", "ich (Subject)", "Deutsch."),
                            listOf("In Berlin (Place)", "wohnt", "mein Bruder", "seit zwei Jahren.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Morgen fahre ich nach Hamburg.",
                            highlightWord = "fahre ich",
                            english = "Tomorrow I am traveling to Hamburg.",
                            note = "Inversion: Time adverb 'Morgen' takes Position 1, verb 'fahre' is in Position 2, subject 'ich' moves to Position 3."
                        ),
                        GrammarExample(
                            german = "Am Wochenende schlafe ich immer lange.",
                            highlightWord = "schlafe ich",
                            english = "On the weekend I always sleep late.",
                            note = "Verb in Position 2 right after the prepositional phrase."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_a1_03_question_formation",
                    title = "Question Types: W-Fragen & Ja/Nein-Fragen",
                    explanation = "W-questions begin with a question pronoun (wer, was, wo, wann, wie, warum, woher, wohin) followed immediately by the verb. Yes/No questions place the verb in Position 1.",
                    table = GrammarTable(
                        headers = listOf("Question Type", "Position 1", "Position 2", "Position 3 / Rest"),
                        rows = listOf(
                            listOf("W-Frage (Place)", "Wo", "wohnst", "du?"),
                            listOf("W-Frage (Origin)", "Woher", "kommen", "Sie?"),
                            listOf("W-Frage (Time)", "Wann", "beginnt", "der Deutschkurs?"),
                            listOf("Ja/Nein-Frage", "Kommst", "du", "heute zum Unterricht?"),
                            listOf("Ja/Nein-Frage", "Haben", "Sie", "einen Moment Zeit?")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Wann hast du heute Feierabend?",
                            highlightWord = "Wann hast du",
                            english = "When do you finish work today?",
                            note = "W-Frage: Question word 'Wann' in Pos 1, verb 'hast' in Pos 2."
                        ),
                        GrammarExample(
                            german = "Verstehst du diesen Satz?",
                            highlightWord = "Verstehst du",
                            english = "Do you understand this sentence?",
                            note = "Ja/Nein-Frage: Verb 'Verstehst' directly in Position 1."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.4: NOMINATIV & AKKUSATIV
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_04_nom_akk",
            number = 4,
            level = CefrLevel.A1,
            title = "Nominativ & Akkusativ Cases",
            germanTitle = "Nominativ & Akkusativ",
            subtitle = "Definite & Indefinite Articles, Direct Objects",
            category = "Cases & Nouns",
            iconEmoji = "📦",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The Nominativ is the subject (who/what does the action). The Akkusativ is the direct object (who/what is affected). In Akkusativ, ONLY masculine articles change: der -> den, ein -> einen, kein -> keinen. Feminine, neuter, and plural remain unchanged!",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Only masculine changes in the Akkusativ! 'der' becomes 'den', 'ein' becomes 'einen'. Feminine (die/eine), neuter (das/ein), and plural (die) do NOT change.",
                "Verbs that always take Akkusativ: haben, brauchen, kaufen, sehen, essen, trinken, anrufen, besuchen.",
                "Nominativ after 'sein' and 'werden': 'Das ist ein Tisch' (NOT einen Tisch), because 'sein' links two Nominatives (predicate noun)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_04_declension_table",
                    title = "Nominativ vs. Akkusativ Articles",
                    explanation = "Notice how only the masculine column changes from 'der/ein' to 'den/einen'. All other genders look identical in Nominativ and Akkusativ.",
                    table = GrammarTable(
                        headers = listOf("Case", "Masculine", "Feminine", "Neuter", "Plural"),
                        rows = listOf(
                            listOf("Nominativ (Subject)", "der / ein / kein", "die / eine / keine", "das / ein / kein", "die / - / keine"),
                            listOf("Akkusativ (Direct Object)", "den / einen / keinen", "die / eine / keine", "das / ein / kein", "die / - / keine")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe einen großen Hund und eine Katze.",
                            highlightWord = "einen großen Hund",
                            english = "I have a big dog and a cat.",
                            note = "'der Hund' is masculine -> 'einen Hund' in Akkusativ. 'die Katze' stays 'eine Katze'."
                        ),
                        GrammarExample(
                            german = "Er sucht den Schlüssel für die Wohnung.",
                            highlightWord = "den Schlüssel",
                            english = "He is looking for the key to the apartment.",
                            note = "'der Schlüssel' is masculine direct object of 'suchen' -> 'den Schlüssel'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.5: NEGATION: NICHT VS. KEIN
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_05_negation",
            number = 5,
            level = CefrLevel.A1,
            title = "Negation: nicht vs. kein",
            germanTitle = "Verneinung: nicht vs. kein",
            subtitle = "Negating Nouns, Verbs, Adjectives & Clauses",
            category = "Sentence Structure",
            iconEmoji = "🚫",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Use 'kein' to negate nouns that would take an indefinite article (ein/eine) or have no article (zero article). Use 'nicht' to negate verbs, adjectives, adverbs, proper names, and nouns with definite articles.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Never say 'Ich habe nicht Auto'. Because 'Auto' is a noun that would take 'ein Auto', you MUST negate it with 'Ich habe kein Auto'.",
                "Definite nouns take 'nicht': 'Ich kenne den Mann nicht' (because 'den Mann' has a definite article).",
                "Position of 'nicht': When negating a whole sentence, 'nicht' moves to the very end: 'Ich komme heute nicht'."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_05_rules",
                    title = "When to Use 'kein' vs. 'nicht'",
                    explanation = "'kein' declines exactly like 'ein' in the singular, plus has plural forms (keine). 'nicht' is invariable.",
                    table = GrammarTable(
                        headers = listOf("Negation Word", "Used For", "Positive Form", "Negated Form"),
                        rows = listOf(
                            listOf("kein / keine", "Nouns with ein/eine", "Ich habe ein Auto.", "Ich habe kein Auto."),
                            listOf("keine (plural)", "Plural without article", "Ich habe Geschwister.", "Ich habe keine Geschwister."),
                            listOf("kein (uncountable)", "Uncountable zero article", "Ich trinke Kaffee.", "Ich trinke keinen Kaffee."),
                            listOf("nicht", "Verbs (actions)", "Ich arbeite heute.", "Ich arbeite heute nicht."),
                            listOf("nicht", "Adjectives / Adverbs", "Das Essen ist gut.", "Das Essen ist nicht gut."),
                            listOf("nicht", "Nouns with 'der/die/das'", "Ich kenne den Chef.", "Ich kenne den Chef nicht.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe heute leider keine Zeit.",
                            highlightWord = "keine Zeit",
                            english = "Unfortunately I have no time today.",
                            note = "'Zeit' is a noun without article -> negated with 'keine'."
                        ),
                        GrammarExample(
                            german = "Er versteht die Frage nicht.",
                            highlightWord = "nicht",
                            english = "He does not understand the question.",
                            note = "'die Frage' has a definite article, so the sentence is negated with 'nicht' at the end."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.6: POSSESSIVE ARTICLES
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_06_possessives",
            number = 6,
            level = CefrLevel.A1,
            title = "Possessive Articles (Nom. & Akk.)",
            germanTitle = "Possessivartikel (Nominativ & Akkusativ)",
            subtitle = "mein, dein, sein, ihr, unser, euer, ihr/Ihr",
            category = "Pronouns",
            iconEmoji = "🤝",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Possessive articles show ownership. The base root depends on the owner (ich -> mein, du -> dein, er -> sein, sie -> ihr, wir -> unser, ihr -> euer, sie/Sie -> ihr/Ihr). The ending depends on the gender and case of the possessed noun!",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "euer drops the internal -e- when an ending is attached: euer -> eure (feminine/plural), euren (masculine accusative).",
                "sein vs. ihr: 'sein' = his/its (Peter -> sein Buch); 'ihr' = her (Maria -> ihr Buch).",
                "Masculine Akkusativ takes -en: 'Ich besuche meinen Freund' (NOT 'mein Freund')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_06_possessive_roots",
                    title = "Possessive Roots by Owner",
                    explanation = "Identify the owner to choose the stem, then decline it like 'ein/kein'.",
                    table = GrammarTable(
                        headers = listOf("Owner", "Possessive Root", "Example (Nom. Masculine)", "Example (Nom. Feminine)"),
                        rows = listOf(
                            listOf("ich", "mein", "mein Vater", "meine Mutter"),
                            listOf("du", "dein", "dein Bruder", "deine Schwester"),
                            listOf("er / es", "sein", "sein Sohn", "seine Tochter"),
                            listOf("sie (she)", "ihr", "ihr Mann", "ihre Frau"),
                            listOf("wir", "unser", "unser Haus", "unsere Schule"),
                            listOf("ihr (you all)", "euer", "euer Hund", "eure Katze"),
                            listOf("sie (they)", "ihr", "ihr Garten", "ihre Wohnung"),
                            listOf("Sie (formal)", "Ihr", "Ihr Pass", "Ihre Fahrkarte")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Hast du meinen Autoschlüssel gesehen?",
                            highlightWord = "meinen Autoschlüssel",
                            english = "Have you seen my car key?",
                            note = "'der Schlüssel' is masculine direct object (Akkusativ) -> 'meinen'."
                        ),
                        GrammarExample(
                            german = "Das ist unsere neue Wohnung.",
                            highlightWord = "unsere neue Wohnung",
                            english = "This is our new apartment.",
                            note = "'die Wohnung' is feminine Nominativ -> 'unsere'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.7: NOUN PLURAL PATTERNS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_07_plurals",
            number = 7,
            level = CefrLevel.A1,
            title = "Noun Plural Patterns",
            germanTitle = "Pluralbildung der Nomen",
            subtitle = "-e, -er, -en/-n, -s & Umlaut Shifts",
            category = "Cases & Nouns",
            iconEmoji = "👥",
            priority = ExamPriority.CORE_B1,
            summary = "Unlike English which mostly adds -s, German has 5 primary plural patterns, often combined with an Umlaut (ä, ö, ü). Always learn each noun together with its article and its plural form.",
            targetGameType = GameType.DER_DIE_DAS,
            examTraps = listOf(
                "All German nouns in the plural take the definite article 'die' in the Nominative and Accusative.",
                "Feminine nouns ending in -e, -in, -ung, -heit, -keit, -schaft, -tät almost always form their plural with -(e)n (die Zeitung -> die Zeitungen, die Lehrerin -> die Lehrerinnen).",
                "Foreign loanwords (from English/French) typically add -s (das Auto -> die Autos, das Handy -> die Handys)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_07_plural_patterns",
                    title = "The 5 Plural Patterns",
                    explanation = "Summary of the 5 main plural patterns with typical gender tendencies.",
                    table = GrammarTable(
                        headers = listOf("Ending", "Umlaut?", "Typical Gender", "Singular -> Plural"),
                        rows = listOf(
                            listOf("-(e)n", "Never", "Most feminine (90%)", "die Frau -> die Frauen, die Lampe -> die Lampen"),
                            listOf("-e", "Often", "Many masculine & neuter", "der Tag -> die Tage, der Arzt -> die Ärzte"),
                            listOf("-er", "Almost always", "Many neuter & some masc.", "das Kind -> die Kinder, das Buch -> die Bücher"),
                            listOf("- (no ending)", "Often", "Masc./neut. in -el, -en, -er", "der Apfel -> die Äpfel, der Lehrer -> die Lehrer"),
                            listOf("-s", "Never", "Loanwords, abbreviations", "das Foto -> die Fotos, das Hotel -> die Hotels")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Die Kinder spielen im Garten mit den Bällen.",
                            highlightWord = "Die Kinder",
                            english = "The children are playing in the garden with the balls.",
                            note = "'das Kind' -> 'die Kinder' (-er plural pattern)."
                        ),
                        GrammarExample(
                            german = "Ich muss noch zwei wichtige Termine absagen.",
                            highlightWord = "Termine",
                            english = "I still have to cancel two important appointments.",
                            note = "'der Termin' -> 'die Termine' (-e plural pattern)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.8: MODAL VERBS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_08_modals",
            number = 8,
            level = CefrLevel.A1,
            title = "Modal Verbs (Present Tense)",
            germanTitle = "Modalverben im Präsens",
            subtitle = "können, müssen, wollen, möchten, dürfen, sollen",
            category = "Verbs",
            iconEmoji = "🚦",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Modal verbs modify the action of another verb (ability, obligation, permission, desire). The modal verb is conjugated in Position 2, while the main action verb is placed at the very end in its infinitive form (Satzklammer).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "In all modal verbs, 'ich' and 'er/sie/es' have identical forms with NO ending (ich kann, er kann; ich muss, er muss; ich will, er will).",
                "müssen (obligation/must) vs. dürfen (permission/allowed to). Negation trap: 'nicht müssen' = don't have to (optional), but 'nicht dürfen' = strictly forbidden (must not!).",
                "The main verb MUST stay at the very end of the clause in the infinitive: 'Ich muss heute viel arbeiten' (NOT 'Ich muss arbeiten heute viel')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_08_conjugation_table",
                    title = "Modal Verb Conjugation Table",
                    explanation = "Notice the vowel change in the singular and the identical forms for 'ich' and 'er/sie/es'.",
                    table = GrammarTable(
                        headers = listOf("Infinitive", "Meaning", "ich / er/sie/es", "du", "wir / sie/Sie"),
                        rows = listOf(
                            listOf("können", "can / able to", "kann", "kannst", "können"),
                            listOf("müssen", "must / have to", "muss", "musst", "müssen"),
                            listOf("wollen", "want to (firm)", "will", "willst", "wollen"),
                            listOf("möchten", "would like to", "möchte", "möchtest", "möchten"),
                            listOf("dürfen", "may / allowed to", "darf", "darfst", "dürfen"),
                            listOf("sollen", "should / supposed to", "soll", "sollst", "sollen")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich möchte gern einen Kaffee mit Milch bestellen.",
                            highlightWord = "möchte ... bestellen",
                            english = "I would like to order a coffee with milk.",
                            note = "Satzklammer: Modal verb 'möchte' in Position 2, infinitive 'bestellen' at the end."
                        ),
                        GrammarExample(
                            german = "Hier darf man leider nicht parken.",
                            highlightWord = "darf ... parken",
                            english = "Unfortunately you are not allowed to park here.",
                            note = "'nicht dürfen' expresses a prohibition."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.9: IMPERATIVE MOOD
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_09_imperative",
            number = 9,
            level = CefrLevel.A1,
            title = "The Imperative Mood (Commands)",
            germanTitle = "Der Imperativ (Befehlsform)",
            subtitle = "du-, ihr- and Sie-forms for Instructions & Advice",
            category = "Verbs",
            iconEmoji = "📢",
            priority = ExamPriority.CORE_B1,
            summary = "The imperative is used for commands, advice, invitations, and requests. German has 3 forms depending on who you address: du (singular informal), ihr (plural informal), and Sie (formal).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "du-form drops the subject 'du' AND the ending '-st': du kommst -> Komm! (NOT: Du komm!).",
                "Strong verbs with e -> i/ie keep the vowel change in the du-imperative (du liest -> Lies! du sprichst -> Sprich!), but verbs with a -> ä lose the umlaut (du fährst -> Fahr!).",
                "The formal Sie-form keeps the pronoun 'Sie', but inverts the order: Kommen Sie bitte herein!"
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_09_three_forms",
                    title = "Forming the 3 Imperatives",
                    explanation = "How to build commands for du, ihr, and formal Sie.",
                    table = GrammarTable(
                        headers = listOf("Form", "How to form", "machen", "warten", "sprechen (e->i)", "sein (irregular)"),
                        rows = listOf(
                            listOf("du (1 person informal)", "Verb stem (no -st, no 'du')", "Mach!", "Warte!", "Sprich!", "Sei!"),
                            listOf("ihr (multiple informal)", "Present ihr-form (no 'ihr')", "Macht!", "Wartet!", "Sprecht!", "Seid!"),
                            listOf("Sie (formal)", "Infinitive + 'Sie'", "Machen Sie!", "Warten Sie!", "Sprechen Sie!", "Seien Sie!")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Fahr bitte vorsichtig, es schneit!",
                            highlightWord = "Fahr",
                            english = "Drive carefully please, it is snowing!",
                            note = "du-imperative of 'fahren' loses the umlaut (Fahr!, not Fähr!)."
                        ),
                        GrammarExample(
                            german = "Öffnen Sie bitte das Buch auf Seite zwanzig.",
                            highlightWord = "Öffnen Sie",
                            english = "Please open the book to page twenty.",
                            note = "Formal imperative: Infinitive 'Öffnen' + pronoun 'Sie' + 'bitte'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.10: SEPARABLE VERBS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_10_separable",
            number = 10,
            level = CefrLevel.A1,
            title = "Separable Verbs (Trennbare Verben)",
            germanTitle = "Trennbare Verben",
            subtitle = "Prefixes that Detach to Clause End",
            category = "Verbs",
            iconEmoji = "✂️",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Separable verbs consist of a base verb and a stressed prefix (e.g. aufstehen, einkaufen, anrufen). In the present tense main clause, the prefix detaches and moves to the very end of the sentence.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Always separable prefixes: ab-, an-, auf-, aus-, ein-, mit-, nach-, vor-, weg-, zu-, zurück-.",
                "Inseparable prefixes (never split!): be-, ent-, er-, ge-, miss-, ver-, zer- (e.g. bezahlen, verstehen, erklären).",
                "When a modal verb is used, the separable verb does NOT split: 'Ich muss morgen früh aufstehen' (stays joined as an infinitive at the end)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_10_split_rule",
                    title = "Separable Verb Sentence Bracket",
                    explanation = "The stem is conjugated in Position 2; the prefix lands at the final position of the clause.",
                    table = GrammarTable(
                        headers = listOf("Prefix", "Infinitive", "Meaning", "Example in Present Tense"),
                        rows = listOf(
                            listOf("auf-", "aufstehen", "to get up", "Ich stehe um 6 Uhr auf."),
                            listOf("an-", "anrufen", "to call (phone)", "Er ruft seine Mutter an."),
                            listOf("ein-", "einkaufen", "to go shopping", "Wir kaufen im Supermarkt ein."),
                            listOf("mit-", "mitkommen", "to come along", "Kommst du heute Abend mit?"),
                            listOf("aus-", "aussteigen", "to get off (bus/train)", "Sie steigt am Hauptbahnhof aus."),
                            listOf("fern-", "fernsehen", "to watch TV", "Er sieht jeden Abend fern.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich stehe jeden Werktag um sechs Uhr auf.",
                            highlightWord = "stehe ... auf",
                            english = "I get up at six o'clock every workday.",
                            note = "'aufstehen' splits: conjugated stem 'stehe' in Pos 2, prefix 'auf' at clause end."
                        ),
                        GrammarExample(
                            german = "Wann fängt der Film heute Abend an?",
                            highlightWord = "fängt ... an",
                            english = "When does the movie start this evening?",
                            note = "'anfangen' splits: 'fängt' (with a->ä shift) in Pos 2, prefix 'an' at the end."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.11: PERFEKT TENSE INTRODUCTION
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_11_perfekt_intro",
            number = 11,
            level = CefrLevel.A1,
            title = "Perfekt Tense (Spoken Past Intro)",
            germanTitle = "Das Perfekt (Einführung)",
            subtitle = "haben/sein + Partizip II (ge-...-t / ge-...-en)",
            category = "Tenses",
            iconEmoji = "⏳",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The Perfekt is the primary past tense used in spoken German and informal writing. It is formed using a helping auxiliary verb (haben or sein) in Position 2 and the past participle (Partizip II) at the end.",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Auxiliary choice: Most verbs take 'haben'. Only verbs of motion (gehen, fahren, kommen) or change of state (aufwachen, sterben) and 'sein/bleiben' take 'sein'!",
                "Regular Partizip II: ge- + verb stem + -t (lernen -> gelernt, machen -> gemacht).",
                "Verbs ending in -ieren do NOT take 'ge-': studieren -> studiert, reservieren -> reserviert."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_11_perfekt_basics",
                    title = "Forming the Perfekt",
                    explanation = "Auxiliary verb (haben / sein) in Position 2 + Partizip II at the very end.",
                    table = GrammarTable(
                        headers = listOf("Verb Type", "Infinitive", "Auxiliary", "Partizip II", "Example"),
                        rows = listOf(
                            listOf("Regular", "lernen", "haben", "gelernt", "Ich habe Deutsch gelernt."),
                            listOf("Regular in -ieren", "fotografieren", "haben", "fotografiert", "Er hat viele Fotos fotografiert."),
                            listOf("Separable regular", "einkaufen", "haben", "eingekauft", "Wir haben im Supermarkt eingekauft."),
                            listOf("Irregular (motion)", "gehen", "sein", "gegangen", "Ich bin nach Hause gegangen."),
                            listOf("Irregular (motion)", "fahren", "sein", "gefahren", "Sie ist nach Berlin gefahren.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Gestern habe ich mit meiner Familie telefoniert.",
                            highlightWord = "habe ... telefoniert",
                            english = "Yesterday I talked on the phone with my family.",
                            note = "'telefonieren' ends in -ieren, takes 'haben' and has no 'ge-' prefix."
                        ),
                        GrammarExample(
                            german = "Wann bist du gestern Abend nach Hause gekommen?",
                            highlightWord = "bist ... gekommen",
                            english = "When did you come home yesterday evening?",
                            note = "'kommen' expresses movement, so it uses 'sein' as auxiliary."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.12: BASIC PREPOSITIONS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_12_basic_prepositions",
            number = 12,
            level = CefrLevel.A1,
            title = "Basic Prepositions & Fixed Expressions",
            germanTitle = "Grundlegende Präpositionen & Feste Wendungen",
            subtitle = "Time (am, um, im) and Place (in, aus, nach)",
            category = "Prepositions",
            iconEmoji = "📍",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Prepositions connect words to show time, place, or direction. At A1, focus on fixed temporal expressions (um, am, im) and common local prepositions (aus, nach, in).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Time prepositions formula: 'um' for clock time (um 8 Uhr), 'am' for days and parts of day (am Montag, am Morgen), 'im' for months and seasons (im Juli, im Sommer).",
                "Contractions: an + dem = am; in + dem = im; zu + der = zur; zu + dem = zum.",
                "Origin vs. Destination: 'aus' = out of / from a city or country (Ich komme aus Deutschland); 'nach' = to a city or country without article (Ich fahre nach Berlin)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_12_time_place",
                    title = "Time & Place Preposition Guide",
                    explanation = "Master the core time and place prepositions that appear in every A1 conversation and email.",
                    table = GrammarTable(
                        headers = listOf("Preposition", "Use Case", "Examples", "English"),
                        rows = listOf(
                            listOf("um", "Clock time", "um 14:30 Uhr", "at 2:30 PM"),
                            listOf("am (an dem)", "Days, dates, parts of day", "am Montag, am 1. Mai, am Abend", "on Monday, in the evening"),
                            listOf("im (in dem)", "Months, seasons, years", "im Mai, im Winter, im Jahr 2024", "in May, in winter"),
                            listOf("aus", "Origin (country/city/home)", "aus Italien, aus dem Haus", "from Italy, out of the house"),
                            listOf("nach", "Direction (cities, countries)", "nach München, nach Hause", "to Munich, to home"),
                            listOf("mit", "Means of transport / with (+Dat)", "mit dem Bus, mit meiner Freundin", "by bus, with my girlfriend")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Der Kurs beginnt am Montag um neun Uhr.",
                            highlightWord = "am Montag um neun Uhr",
                            english = "The course begins on Monday at nine o'clock.",
                            note = "'am' for the weekday, 'um' for the specific clock time."
                        ),
                        GrammarExample(
                            german = "Ich fahre jeden Morgen mit dem Zug zur Arbeit.",
                            highlightWord = "mit dem Zug zur Arbeit",
                            english = "I take the train to work every morning.",
                            note = "'mit' + Dative masculine 'dem Zug'; 'zur' = zu + der (Dative feminine)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A1.13: "ES GIBT" & NUMBERS, TIME, DATES
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a1_13_es_gibt_numbers",
            number = 13,
            level = CefrLevel.A1,
            title = "“Es gibt” & Numbers, Time, Dates",
            germanTitle = "„Es gibt“ & Zahlen, Uhrzeit, Datum",
            subtitle = "There is / are (+ Akkusativ), Clock Times & Days",
            category = "Sentence Structure",
            iconEmoji = "🔢",
            priority = ExamPriority.CORE_B1,
            summary = "'Es gibt' means 'there is' or 'there are' and ALWAYS takes an Akkusativ object. Telling time, cardinal numbers, and dates are daily conversational essentials.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "'es gibt' ALWAYS governs the Akkusativ: 'Es gibt einen Supermarkt' (NOT: ein Supermarkt). It is always singular ('es gibt', never 'es geben').",
                "German clock times: 'halb acht' means 7:30 (halfway to eight!), NOT 8:30.",
                "Two-digit numbers: Germans say the units first, then the tens: 24 = vierundzwanzig (four-and-twenty)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a1_13_es_gibt_rules",
                    title = "Using “es gibt” (+ Akkusativ)",
                    explanation = "'Es gibt' corresponds to both English 'there is' and 'there are'. The following noun is always in the Akkusativ case.",
                    examples = listOf(
                        GrammarExample(
                            german = "In unserer Straße gibt es einen sehr guten Bäcker.",
                            highlightWord = "gibt es einen",
                            english = "In our street there is a very good bakery.",
                            note = "'der Bäcker' is masculine, so it takes 'einen' in the Akkusativ."
                        ),
                        GrammarExample(
                            german = "Gibt es hier in der Nähe ein Krankenhaus?",
                            highlightWord = "Gibt es",
                            english = "Is there a hospital nearby?",
                            note = "Question form with inverted verb: 'Gibt es...?'"
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_a1_13_time_clock",
                    title = "Telling Time & Numbers",
                    explanation = "Summary of formal (24h) and informal clock time expressions.",
                    table = GrammarTable(
                        headers = listOf("Time", "Informal (Conversational)", "Formal (Radio/Official)", "Explanation"),
                        rows = listOf(
                            listOf("08:00", "acht Uhr", "acht Uhr", "exact hour"),
                            listOf("08:15", "Viertel nach acht", "acht Uhr fünfzehn", "quarter past"),
                            listOf("08:30", "halb neun", "acht Uhr dreißig", "halfway to nine!"),
                            listOf("08:45", "Viertel vor neun", "acht Uhr fünfundvierzig", "quarter to nine"),
                            listOf("08:20", "zwanzig nach acht / zehn vor halb neun", "acht Uhr zwanzig", "minutes past / before")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Wir treffen uns heute um halb sieben vor dem Kino.",
                            highlightWord = "halb sieben",
                            english = "We meet today at half past six in front of the cinema.",
                            note = "'halb sieben' = 6:30 PM."
                        ),
                        GrammarExample(
                            german = "Der Zug fährt um vierzehn Uhr fünfzehn ab.",
                            highlightWord = "vierzehn Uhr fünfzehn",
                            english = "The train departs at 14:15.",
                            note = "Formal 24-hour time format."
                        )
                    )
                )
            )
        )
    )
}
