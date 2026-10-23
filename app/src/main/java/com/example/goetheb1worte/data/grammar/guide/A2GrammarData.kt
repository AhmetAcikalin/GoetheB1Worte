package com.example.goetheb1worte.data.grammar.guide

import com.example.goetheb1worte.data.repository.GameType

object A2GrammarData {

    val topics: List<GrammarGuideTopic> = listOf(
        // ---------------------------------------------------------------------
        // A2.1: DATIV CASE & DATIVE-ONLY VERBS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_01_dativ",
            number = 1,
            level = CefrLevel.A2,
            title = "Dativ Case & Dative-Only Verbs",
            germanTitle = "Der Dativ & Dativ-Verben",
            subtitle = "Articles, Pronouns (mir/dir) & Core Dative Verbs",
            category = "Cases & Nouns",
            iconEmoji = "🎁",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The Dativ answers 'Wem?' (To/for whom?) as the indirect receiver of an action. Articles shift to dem / der / dem / den + -n. Crucially, certain German verbs demand a Dativ object without any preposition (helfen, danken, gefallen, gehören, schmecken).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Plural Dativ trap: Nouns add an extra '-n' in Dativ plural if they don't already end in -n or -s (den Kindern, den Freunden, den Büchern).",
                "Dative-only verbs trap: In English you help 'someone' (direct object), but in German you help 'TO someone' (Dativ): 'Ich helfe dir' (NOT: 'Ich helfe dich').",
                "Personal pronouns in Dativ: mir, dir, ihm, ihr, ihm, uns, euch, ihnen/Ihnen."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_01_dativ_forms",
                    title = "Dativ Articles & Personal Pronouns",
                    explanation = "In the Dativ case, masculine and neuter take 'dem / einem', feminine takes 'der / einer', and plural takes 'den / keinen' plus adding an '-n' to the noun.",
                    table = GrammarTable(
                        headers = listOf("Gender / Number", "Definite Article", "Indefinite Article", "Pronoun Form (Nom -> Dat)"),
                        rows = listOf(
                            listOf("Masculine", "dem", "einem / keinem", "er -> ihm"),
                            listOf("Feminine", "der", "einer / keiner", "sie -> ihr"),
                            listOf("Neuter", "dem", "einem / keinem", "es -> ihm"),
                            listOf("Plural", "den (+ n)", "- / keinen (+ n)", "sie -> ihnen / Sie -> Ihnen")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich helfe meinem Vater bei der Gartenarbeit.",
                            highlightWord = "meinem Vater",
                            english = "I am helping my father with the garden work.",
                            note = "'helfen' requires Dative -> masculine 'meinem Vater'."
                        ),
                        GrammarExample(
                            german = "Wie gefällt dir deine neue Wohnung?",
                            highlightWord = "dir",
                            english = "How do you like your new apartment?",
                            note = "'gefallen' requires Dative person pronoun 'dir'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_a2_01_dative_verbs",
                    title = "Core Verbs with Fixed Dativ",
                    explanation = "Memorize these high-frequency verbs that strictly govern the Dativ case.",
                    table = GrammarTable(
                        headers = listOf("Verb", "Meaning", "Example Sentence"),
                        rows = listOf(
                            listOf("helfen", "to help", "Kannst du mir bitte helfen?"),
                            listOf("danken", "to thank", "Ich danke Ihnen für die Einladung."),
                            listOf("gefallen", "to please / to like", "Das neue Kleid gefällt ihr sehr."),
                            listOf("gehören", "to belong to", "Wem gehört dieses Fahrrad?"),
                            listOf("schmecken", "to taste good to", "Die Suppe schmeckt den Kindern nicht."),
                            listOf("passen", "to fit / suit", "Der Termin passt mir gut.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Das Buch gehört der Lehrerin.",
                            highlightWord = "gehört der Lehrerin",
                            english = "The book belongs to the teacher.",
                            note = "'gehören' + Dativ feminine 'der Lehrerin'."
                        ),
                        GrammarExample(
                            german = "Ich danke dir für deine Hilfe.",
                            highlightWord = "danke dir",
                            english = "I thank you for your help.",
                            note = "'danken' + Dativ pronoun 'dir'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.2: WECHSELPRÄPOSITIONEN (TWO-WAY PREPOSITIONS INTRO)
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_02_wechselpraep",
            number = 2,
            level = CefrLevel.A2,
            title = "Two-Way Prepositions (Wo vs. Wohin)",
            germanTitle = "Wechselpräpositionen (Wo vs. Wohin)",
            subtitle = "an, auf, hinter, in, neben, über, unter, vor, zwischen",
            category = "Prepositions",
            iconEmoji = "🧭",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The 9 two-way prepositions take Dativ when answering 'Wo?' (static position or location) and Akkusativ when answering 'Wohin?' (directional movement or destination).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Wo? (Position/Zustand) = Dativ! Wohin? (Richtung/Bewegung) = Akkusativ!",
                "Verb pairs: stellen/stehen, legen/liegen, setzen/sitzen, hängen/hängen. Active placing takes Akkusativ, resting state takes Dativ: 'Ich stelle die Tasse auf den Tisch' (Akk) vs. 'Die Tasse steht auf dem Tisch' (Dat).",
                "Moving within a location vs. moving into a location: 'Ich jogge im Park' (Wo? inside the park = Dativ) vs. 'Ich laufe in den Park' (Wohin? entering the park = Akkusativ)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_02_wo_wohin_table",
                    title = "Wo? (Dativ) vs. Wohin? (Akkusativ)",
                    explanation = "Test the sentence with 'Wo?' or 'Wohin?' to determine the case.",
                    table = GrammarTable(
                        headers = listOf("Preposition", "Wo? + Dativ (Location)", "Wohin? + Akkusativ (Destination)"),
                        rows = listOf(
                            listOf("in", "Ich bin in der Schule.", "Ich gehe in die Schule."),
                            listOf("auf", "Das Buch liegt auf dem Tisch.", "Ich lege das Buch auf den Tisch."),
                            listOf("an", "Das Bild hängt an der Wand.", "Ich hänge das Bild an die Wand."),
                            listOf("unter", "Die Katze schläft unter dem Bett.", "Die Katze läuft unter das Bett."),
                            listOf("vor", "Das Auto parkt vor dem Haus.", "Er fährt das Auto vor das Haus.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich gehe jetzt in die Küche.",
                            highlightWord = "in die Küche",
                            english = "I am going into the kitchen now.",
                            note = "Wohin? (Destination) -> 'in' + Akkusativ feminine 'die Küche'."
                        ),
                        GrammarExample(
                            german = "Meine Mutter kocht schon in der Küche.",
                            highlightWord = "in der Küche",
                            english = "My mother is already cooking in the kitchen.",
                            note = "Wo? (Location) -> 'in' + Dativ feminine 'der Küche'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.3: DATIV PREPOSITIONS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_03_dativ_prepositions",
            number = 3,
            level = CefrLevel.A2,
            title = "Dative Prepositions (aus, bei, mit...)",
            germanTitle = "Dativ-Präpositionen",
            subtitle = "aus, bei, mit, nach, seit, von, zu, gegenüber",
            category = "Prepositions",
            iconEmoji = "🧲",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "These prepositions ALWAYS trigger the Dativ case, regardless of movement, location, or question word: aus, bei, mit, nach, seit, von, zu, gegenüber.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Mnemonic song: 'aus, bei, mit, nach, seit, von, zu' — ALWAYS Dativ, no exceptions!",
                "'seit' is always followed by Dativ: 'seit einem Monat' (NOT: 'seit einen Monat').",
                "Contractions: bei + dem = beim, von + dem = vom, zu + dem = zum, zu + der = zur."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_03_prep_list",
                    title = "The Strict Dativ Prepositions",
                    explanation = "Never use Akkusativ after these prepositions. Always use Dativ.",
                    table = GrammarTable(
                        headers = listOf("Preposition", "Meaning", "Example Sentence", "Contraction"),
                        rows = listOf(
                            listOf("aus", "from / out of", "Er kommt aus dem Büro.", "-"),
                            listOf("bei", "at / with (person/company)", "Ich wohne bei meinen Eltern.", "bei + dem = beim"),
                            listOf("mit", "with / by (transport)", "Fährst du mit dem Bus?", "-"),
                            listOf("nach", "after / to (cities/countries)", "Nach der Arbeit gehe ich einkaufen.", "-"),
                            listOf("seit", "since / for (duration)", "Ich lebe seit einem Jahr hier.", "-"),
                            listOf("von", "from / of", "Das ist ein Geschenk von meiner Oma.", "von + dem = vom"),
                            listOf("zu", "to (persons, buildings)", "Wir gehen zu Fuß zum Bahnhof.", "zu + dem = zum, zu + der = zur"),
                            listOf("gegenüber", "opposite", "Die Bank liegt gegenüber der Post.", "-")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich fahre jeden Morgen mit der U-Bahn zur Uni.",
                            highlightWord = "mit der U-Bahn zur Uni",
                            english = "I take the subway to university every morning.",
                            note = "'mit' + Dative feminine 'der U-Bahn'; 'zur' = zu + der (Dative feminine)."
                        ),
                        GrammarExample(
                            german = "Er wohnt schon seit drei Monaten in Deutschland.",
                            highlightWord = "seit drei Monaten",
                            english = "He has been living in Germany for three months.",
                            note = "'seit' + Dative plural 'drei Monaten' (adds -n to Monat)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.4: PERFEKT: FULL RANGE (HABEN VS. SEIN)
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_04_perfekt_full",
            number = 4,
            level = CefrLevel.A2,
            title = "Perfekt Tense: Full Range",
            germanTitle = "Das Perfekt: haben vs. sein",
            subtitle = "Movement, State Change, Irregular & Inseparable Verbs",
            category = "Tenses",
            iconEmoji = "⏱️",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Expand your Perfekt mastery across all verb types: choosing between 'haben' and 'sein' with precision, forming irregular strong participles (ge-...-en), and mastering separable (aufgestanden) vs. inseparable (verstanden) participles.",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "sein verbs rule: Verbs of movement from A to B (fahren, fliegen, laufen, umziehen), verbs of change of condition (einschlafen, aufwachen, sterben), and 'sein', 'bleiben', 'werden'.",
                "Inseparable verbs (be-, ver-, zer-, ent-, er-, ge-) NEVER take 'ge-': bezahlen -> bezahlt, vergessen -> vergessen, erklären -> erklärt.",
                "Separable verbs insert '-ge-' between prefix and stem: aufstehen -> auf-ge-standen, einkaufen -> ein-ge-kauft."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_04_haben_vs_sein",
                    title = "When to Use 'sein' as Auxiliary",
                    explanation = "Master the verbs that take 'sein'. All transitive verbs (verbs taking an Akkusativ object) take 'haben'.",
                    table = GrammarTable(
                        headers = listOf("Category", "Verbs", "Example in Perfekt"),
                        rows = listOf(
                            listOf("Movement A -> B", "gehen, fahren, fliegen, laufen, kommen, reisen", "Wir sind nach Spanien geflogen."),
                            listOf("Change of State", "aufwachen, einschlafen, sterben, wachsen", "Das Kind ist schnell eingeschlafen."),
                            listOf("Exceptions", "sein, bleiben, werden, passieren", "Ich bin gestern zu Hause geblieben.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Familie Müller ist vor einem Monat nach Berlin umgezogen.",
                            highlightWord = "ist ... umgezogen",
                            english = "The Müller family moved to Berlin a month ago.",
                            note = "'umziehen' (relocating) is a movement from A to B -> takes 'sein'!"
                        ),
                        GrammarExample(
                            german = "Ich habe gestern die Rechnung sofort bezahlt.",
                            highlightWord = "habe ... bezahlt",
                            english = "I paid the invoice immediately yesterday.",
                            note = "'bezahlen' is inseparable (be-) -> takes 'haben' and no 'ge-' prefix."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.5: PRÄTERITUM OF HABEN, SEIN & MODAL VERBS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_05_praeteritum",
            number = 5,
            level = CefrLevel.A2,
            title = "Präteritum of sein, haben & Modals",
            germanTitle = "Präteritum von sein, haben & Modalverben",
            subtitle = "war, hatte, konnte, musste, wollte, durfte, sollte",
            category = "Tenses",
            iconEmoji = "📜",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "While written German uses the Präteritum for all verbs, everyday spoken German strictly prefers the Präteritum forms of 'sein' (war), 'haben' (hatte), and all modal verbs (konnte, musste, wollte, durfte, sollte) instead of their cumbersome Perfekt forms.",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Germans rarely say 'Ich habe Zeit gehabt' or 'Ich bin krank gewesen'. They say 'Ich hatte Zeit' and 'Ich war krank'.",
                "Modal verbs lose their umlaut in the Präteritum: können -> konnte, müssen -> musste, dürfen -> durfte (NOT: könnte/müsste, which are Konjunktiv II!).",
                "In the Präteritum, the 1st person (ich) and 3rd person (er/sie/es) are ALWAYS identical with no ending (ich war, er war; ich hatte, er hatte; ich musste, er musste)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_05_praeteritum_forms",
                    title = "Conjugation of sein, haben & Modals in Präteritum",
                    explanation = "Notice that modal verbs drop their umlauts in the past tense.",
                    table = GrammarTable(
                        headers = listOf("Person", "sein", "haben", "können", "müssen", "wollen"),
                        rows = listOf(
                            listOf("ich", "war", "hatte", "konnte", "musste", "wollte"),
                            listOf("du", "warst", "hattest", "konntest", "musstest", "wolltest"),
                            listOf("er/sie/es", "war", "hatte", "konnte", "musste", "wollte"),
                            listOf("wir", "waren", "hatten", "konnten", "mussten", "wollten"),
                            listOf("ihr", "wart", "hattet", "konntet", "musstet", "wolltet"),
                            listOf("sie/Sie", "waren", "hatten", "konnten", "mussten", "wollten")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich konnte gestern leider nicht kommen, weil ich arbeiten musste.",
                            highlightWord = "konnte ... musste",
                            english = "Unfortunately I could not come yesterday because I had to work.",
                            note = "Präteritum modals: 'konnte' in Pos 2, 'musste' at the end of the subordinate clause."
                        ),
                        GrammarExample(
                            german = "Letztes Jahr war ich für zwei Wochen in Italien im Urlaub.",
                            highlightWord = "war ich",
                            english = "Last year I was on vacation in Italy for two weeks.",
                            note = "Natural past tense of 'sein' in spoken German."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.6: COMPARATIVE & SUPERLATIVE INTRO
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_06_comparative",
            number = 6,
            level = CefrLevel.A2,
            title = "Comparative & Superlative Intro",
            germanTitle = "Komparativ & Superlativ (Grundlagen)",
            subtitle = "-er als, so ... wie & Irregular Forms",
            category = "Adjectives",
            iconEmoji = "📊",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Compare things in German using the comparative (-er als) and superlative (am -sten). Equality uses 'so ... wie' (as ... as); inequality uses the comparative + 'als' (than).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "als vs. wie: Use 'wie' for equal comparisons ('genauso groß wie Peter'), use 'als' for unequal comparisons ('größer als Peter'). Never say 'größer wie'!",
                "Short one-syllable adjectives with a, o, u often take an umlaut: alt -> älter -> am ältesten, groß -> größer -> am größten.",
                "Crucial irregulars: gut -> besser -> am besten; viel -> mehr -> am meisten; gern -> lieber -> am liebsten."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_06_comparisons_table",
                    title = "Regular & Irregular Comparisons",
                    explanation = "Summary of comparative (-er) and predicative superlative (am -sten).",
                    table = GrammarTable(
                        headers = listOf("Base Form (Positiv)", "Comparative (Komparativ)", "Superlative (Superlativ)", "Note"),
                        rows = listOf(
                            listOf("schnell", "schneller", "am schnellsten", "regular"),
                            listOf("alt", "älter", "am ältesten", "umlaut a -> ä, inserts -e-"),
                            listOf("groß", "größer", "am größten", "umlaut o -> ö"),
                            listOf("gut", "besser", "am besten", "irregular stem"),
                            listOf("viel", "mehr", "am meisten", "irregular stem"),
                            listOf("gern", "lieber", "am liebsten", "irregular stem")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Der Zug ist schneller als der Bus.",
                            highlightWord = "schneller als",
                            english = "The train is faster than the bus.",
                            note = "Comparative with 'als' for difference."
                        ),
                        GrammarExample(
                            german = "Mein Bruder ist genauso groß wie mein Vater.",
                            highlightWord = "so groß wie",
                            english = "My brother is just as tall as my father.",
                            note = "Equative comparison with 'so ... wie'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.7: SUBORDINATE CLAUSES (WEIL, DASS, WENN)
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_07_subordinate",
            number = 7,
            level = CefrLevel.A2,
            title = "Subordinate Clauses (weil, dass, wenn)",
            germanTitle = "Nebensätze mit weil, dass, wenn",
            subtitle = "Verb-Final Word Order & Conjunctions",
            category = "Sentence Structure",
            iconEmoji = "🔗",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Subordinating conjunctions introduce a dependent clause (Nebensatz) where the conjugated verb is pushed all the way to the very end. The three fundamental A2 conjunctions are 'weil' (because), 'dass' (that), and 'wenn' (if / when).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Verb kicker rule: The finite conjugated verb MUST be at the end of the subordinate clause: '..., weil ich krank bin' (NOT: '..., weil ich bin krank').",
                "Separable verbs join back together at the end of a subordinate clause: '..., weil er um 6 Uhr aufsteht' (NOT: 'auf steht').",
                "Always place a comma before 'weil', 'dass', and 'wenn'."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_07_connectors",
                    title = "Core Subordinating Conjunctions",
                    explanation = "These three conjunctions always kick the conjugated verb to the clause end.",
                    table = GrammarTable(
                        headers = listOf("Conjunction", "Function", "Example Sentence", "Verb Position"),
                        rows = listOf(
                            listOf("weil", "reason (because)", "Ich bleibe im Bett, weil ich krank bin.", "end of clause ('bin')"),
                            listOf("dass", "reported fact (that)", "Ich weiß, dass du recht hast.", "end of clause ('hast')"),
                            listOf("wenn", "condition / time (if / whenever)", "Ich rufe dich an, wenn ich ankomme.", "end of clause ('ankomme')")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich gehe heute nicht zur Arbeit, weil ich Fieber habe.",
                            highlightWord = "weil ich Fieber habe",
                            english = "I am not going to work today because I have a fever.",
                            note = "Subordinating conjunction 'weil': conjugated verb 'habe' at the very end."
                        ),
                        GrammarExample(
                            german = "Ich hoffe, dass wir uns bald wiedersehen.",
                            highlightWord = "dass wir uns bald wiedersehen",
                            english = "I hope that we see each other again soon.",
                            note = "'dass' clause with verb 'wiedersehen' at the final position."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.8: INFINITIV MIT "ZU" INTRO
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_08_infinitiv_zu",
            number = 8,
            level = CefrLevel.A2,
            title = "Infinitive with “zu” Intro",
            germanTitle = "Infinitiv mit „zu“ (Einführung)",
            subtitle = "Zeit haben, Lust haben, wichtig sein + zu + Infinitiv",
            category = "Sentence Structure",
            iconEmoji = "🎯",
            priority = ExamPriority.CORE_B1,
            summary = "When a secondary action depends on verbs expressing intent, plan, feeling, or phrases like 'Zeit haben', 'Lust haben', or 'es ist wichtig', German uses 'zu + Infinitiv' at the end of the clause.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Modal verbs (können, müssen, wollen, dürfen, sollen, möchten) NEVER take 'zu': 'Ich kann schwimmen' (NOT: 'Ich kann zu schwimmen').",
                "Separable verbs insert '-zu-' between prefix and verb: aufzustehen, einzukaufen, mitzubringen.",
                "The infinitive clause has no separate subject (it shares the subject of the main clause)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_08_trigger_phrases",
                    title = "Expressions Triggering 'zu + Infinitiv'",
                    explanation = "Common trigger expressions in everyday German conversation.",
                    table = GrammarTable(
                        headers = listOf("Trigger Expression", "Meaning", "Example Sentence"),
                        rows = listOf(
                            listOf("Zeit haben", "to have time to", "Ich habe keine Zeit, das zu machen."),
                            listOf("Lust haben", "to feel like doing", "Hast du Lust, ins Kino zu gehen?"),
                            listOf("es ist wichtig / schön", "it is important / nice to", "Es ist wichtig, viel Wasser zu trinken."),
                            listOf("planen / vorhaben", "to plan / intend to", "Wir planen, bald umzuziehen."),
                            listOf("versuchen", "to try to", "Ich versuche, pünktlich zu sein.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe keine Lust, heute Abend zu kochen.",
                            highlightWord = "zu kochen",
                            english = "I don't feel like cooking this evening.",
                            note = "'Lust haben' triggers 'zu + Infinitiv' at the end."
                        ),
                        GrammarExample(
                            german = "Vergiss bitte nicht, Milch einzukaufen.",
                            highlightWord = "einzukaufen",
                            english = "Please don't forget to buy milk.",
                            note = "Separable verb: 'ein-zu-kaufen' ('zu' placed between prefix and stem)."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.9: REFLEXIVE VERBS INTRO
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_09_reflexive",
            number = 9,
            level = CefrLevel.A2,
            title = "Reflexive Verbs Intro",
            germanTitle = "Reflexive Verben (Einführung)",
            subtitle = "sich freuen, sich fühlen, sich waschen & Reflexive Pronouns",
            category = "Verbs",
            iconEmoji = "🪞",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Reflexive verbs describe an action that reflects back on the subject (oneself). At A2, focus on common reflexive verbs with Akkusativ pronouns (mich, dich, sich, uns, euch, sich).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "3rd person singular and plural is ALWAYS 'sich' (er freut sich, sie freut sich, sie freuen sich, Sie freuen sich).",
                "Many verbs are reflexive in German but not in English (e.g., sich ausruhen = to rest, sich beeilen = to hurry, sich erinnern = to remember).",
                "Word order: In a main clause, the reflexive pronoun directly follows the conjugated verb: 'Ich freue mich auf das Wochenende'."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_09_pronouns_table",
                    title = "Reflexive Pronouns (Akkusativ)",
                    explanation = "Notice that only 'mich' and 'dich' differ from standard subject pronouns; 3rd person uses 'sich'.",
                    table = GrammarTable(
                        headers = listOf("Subject", "Reflexive Pronoun", "Example Verb (sich freuen)"),
                        rows = listOf(
                            listOf("ich", "mich", "Ich freue mich."),
                            listOf("du", "dich", "Freust du dich?"),
                            listOf("er / sie / es", "sich", "Er freut sich."),
                            listOf("wir", "uns", "Wir freuen uns."),
                            listOf("ihr", "euch", "Freut ihr euch?"),
                            listOf("sie / Sie", "sich", "Sie freuen sich.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich freue mich sehr auf unseren gemeinsamen Urlaub.",
                            highlightWord = "freue mich",
                            english = "I am really looking forward to our vacation together.",
                            note = "'sich freuen auf' + Akkusativ; reflexive pronoun 'mich' for 'ich'."
                        ),
                        GrammarExample(
                            german = "Wie fühlst du dich heute nach der Erkältung?",
                            highlightWord = "fühlst du dich",
                            english = "How do you feel today after the cold?",
                            note = "Reflexive verb 'sich fühlen' with 2nd person pronoun 'dich'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.10: ADJECTIVE ENDINGS INTRO
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_10_adjectives_intro",
            number = 10,
            level = CefrLevel.A2,
            title = "Adjective Endings Intro (Nom. & Akk.)",
            germanTitle = "Adjektivdeklination: Nominativ & Akkusativ",
            subtitle = "Weak & Mixed Endings after der/die/das and ein/eine",
            category = "Adjectives",
            iconEmoji = "🎨",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "When an adjective stands before a noun, it must take an ending. At A2, learn the endings for Nominativ and Akkusativ after definite articles (der/die/das) and indefinite articles (ein/eine).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "After definite articles (der/die/das) in Nominative, the ending is ALWAYS '-e' (der neue Wagen, die schöne Stadt, das kleine Kind).",
                "Masculine Akkusativ ALWAYS takes '-en' everywhere (den neuen Wagen, einen neuen Wagen, meinen neuen Wagen).",
                "Predicative adjectives (standing after 'sein'/'werden') take NO ending: 'Das Auto ist neu' (NOT: 'ist neues')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_10_weak_mixed_table",
                    title = "Adjective Endings in Nom. & Akk.",
                    explanation = "Summary table for adjectives after definite (der) and indefinite (ein) articles.",
                    table = GrammarTable(
                        headers = listOf("Case & Gender", "After Definite (der/die/das)", "After Indefinite (ein/eine)"),
                        rows = listOf(
                            listOf("Nom. Masculine", "der neu-e Computer", "ein neu-er Computer"),
                            listOf("Nom. Feminine", "die neu-e Lampe", "eine neu-e Lampe"),
                            listOf("Nom. Neuter", "das neu-e Buch", "ein neu-es Buch"),
                            listOf("Akk. Masculine", "den neu-en Computer", "einen neu-en Computer"),
                            listOf("Akk. Feminine", "die neu-e Lampe", "eine neu-e Lampe"),
                            listOf("Akk. Neuter", "das neu-e Buch", "ein neu-es Buch")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe mir einen neuen Computer gekauft.",
                            highlightWord = "einen neuen Computer",
                            english = "I bought myself a new computer.",
                            note = "Masculine Akkusativ after 'einen': adjective takes '-en'."
                        ),
                        GrammarExample(
                            german = "Das kleine Kind spielt friedlich im Garten.",
                            highlightWord = "Das kleine Kind",
                            english = "The small child is playing peacefully in the garden.",
                            note = "Neuter Nominativ after definite 'das': adjective takes '-e'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.11: GENITIV INTRO & POSSESSIVE -S
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_11_genitiv_intro",
            number = 11,
            level = CefrLevel.A2,
            title = "Genitiv Intro & Names with -s",
            germanTitle = "Genitiv-Einführung: Namen mit -s",
            subtitle = "Peters Auto, Annas Tasche & von + Dativ",
            category = "Cases & Nouns",
            iconEmoji = "🏷️",
            priority = ExamPriority.CORE_B1,
            summary = "At A2, the Genitiv is introduced through proper names: attach '-s' without an apostrophe (Peters Auto). In spoken German, possession is also very commonly expressed using 'von + Dativ' (das Auto von Peter).",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "No apostrophe in German names: Write 'Peters Auto', NOT 'Peter's Auto' (the apostrophe is an English influence / Deppenapostroph).",
                "Names ending in -s, -ß, -x, -z take an apostrophe only at the end: 'Max' Fahrrad', 'Lukas' Wohnung'.",
                "Colloquial alternative: 'das Auto von meinem Bruder' (von + Dativ is completely accepted in spoken German)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_11_possessive_names",
                    title = "Expressing Possession with Names",
                    explanation = "Attach -s to the proper name before the noun, or use 'von + Dativ'.",
                    examples = listOf(
                        GrammarExample(
                            german = "Peters neues Auto steht vor der Garage.",
                            highlightWord = "Peters neues Auto",
                            english = "Peter's new car is in front of the garage.",
                            note = "German possessive name takes '-s' without apostrophe."
                        ),
                        GrammarExample(
                            german = "Ist das die Tasche von deiner Schwester?",
                            highlightWord = "von deiner Schwester",
                            english = "Is that your sister's bag?",
                            note = "Everyday spoken possession using 'von' + Dativ feminine."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.12: RELATIVE CLAUSES INTRO (NOM. & AKK.)
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_12_relative_intro",
            number = 12,
            level = CefrLevel.A2,
            title = "Relative Clauses Intro (Nom. & Akk.)",
            germanTitle = "Relativsätze: Nominativ & Akkusativ",
            subtitle = "der, die, das, den (Subject & Direct Object)",
            category = "Sentence Structure",
            iconEmoji = "🧩",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Relative clauses provide extra information about a noun. At A2, learn relative clauses where the relative pronoun is in the Nominativ (subject) or Akkusativ (direct object). The pronoun matches the gender of the noun, and the verb goes to the end.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Relative clauses are subordinate clauses: the conjugated verb MUST go to the very end!",
                "Always place a comma before the relative pronoun: 'Das ist der Mann, der hier wohnt.'",
                "Only masculine changes in Akkusativ: 'Das ist der Mann, den ich gestern gesehen habe' (den = Akkusativ masculine)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_12_pronouns_nom_akk",
                    title = "Relative Pronouns in Nom. & Akk.",
                    explanation = "In Nominativ and Akkusativ, relative pronouns look identical to the definite articles (der, die, das, den).",
                    table = GrammarTable(
                        headers = listOf("Gender / Number", "Nominativ (Subject in clause)", "Akkusativ (Object in clause)"),
                        rows = listOf(
                            listOf("Masculine", "der Mann, der hier wohnt", "der Mann, den ich kenne"),
                            listOf("Feminine", "die Frau, die dort steht", "die Frau, die ich mag"),
                            listOf("Neuter", "das Kind, das dort spielt", "das Buch, das ich lese"),
                            listOf("Plural", "die Leute, die hier arbeiten", "die Freunde, die ich einlade")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Kennst du den Mann, der neben Frau Weber wohnt?",
                            highlightWord = "der neben Frau Weber wohnt",
                            english = "Do you know the man who lives next to Mrs. Weber?",
                            note = "Masculine subject in the relative clause -> pronoun 'der', verb 'wohnt' at the end."
                        ),
                        GrammarExample(
                            german = "Das ist der neue Film, den alle sehen wollen.",
                            highlightWord = "den alle sehen wollen",
                            english = "That is the new movie that everyone wants to see.",
                            note = "'der Film' is masculine direct object in the relative clause -> 'den'."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.13: KONJUNKTIV II INTRO (POLITE REQUESTS)
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_13_konjunktiv2_intro",
            number = 13,
            level = CefrLevel.A2,
            title = "Konjunktiv II: Polite Requests",
            germanTitle = "Konjunktiv II: Höfliche Bitten",
            subtitle = "hätte gern, könnte, würde + Infinitiv",
            category = "Verbs",
            iconEmoji = "☕",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "In Germany, politeness is paramount when ordering at restaurants, asking for help, or making requests. Instead of harsh imperatives, use Konjunktiv II: 'Ich hätte gern...', 'Könnten Sie...?', and 'Würden Sie bitte...?'",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "könnte vs. konnte: 'könnte' (with umlaut) is polite Konjunktiv II ('could you?'); 'konnte' (no umlaut) is past tense Präteritum ('was able to').",
                "hätte gern: Expresses polite desire: 'Ich hätte gern eine Tasse Kaffee' (much more polite than 'Ich will einen Kaffee').",
                "würde + Infinitiv: The infinitive goes to the very end: 'Würden Sie bitte das Fenster schließen?'"
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_13_polite_phrases",
                    title = "Polite Request Formulas",
                    explanation = "Memorize these three core patterns for polite everyday communication.",
                    table = GrammarTable(
                        headers = listOf("Formula", "Use Case", "Direct (Impolite)", "Polite Formula"),
                        rows = listOf(
                            listOf("Ich hätte gern...", "Ordering food / goods", "Ich will einen Tee.", "Ich hätte gern einen Tee, bitte."),
                            listOf("Könnten Sie / du...?", "Asking someone to do something", "Hilf mir!", "Könntest du mir bitte helfen?"),
                            listOf("Würden Sie / du...?", "Polite request with verb", "Schreib das auf!", "Würdest du das bitte aufschreiben?")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Könnten Sie mir bitte sagen, wo der Bahnhof ist?",
                            highlightWord = "Könnten Sie mir bitte sagen",
                            english = "Could you please tell me where the train station is?",
                            note = "Polite request with 'könnten Sie'."
                        ),
                        GrammarExample(
                            german = "Ich hätte gern zwei Brötchen und ein Croissant, bitte.",
                            highlightWord = "hätte gern",
                            english = "I would like two bread rolls and a croissant, please.",
                            note = "Polite ordering in a bakery."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.14: ORDINAL NUMBERS, DATES & TIME PREPOSITIONS
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_14_ordinals_time",
            number = 14,
            level = CefrLevel.A2,
            title = "Ordinals, Dates & Time Prepositions",
            germanTitle = "Ordinalzahlen, Datum & Zeitpräpositionen",
            subtitle = "am 1. Mai, vor, nach, seit, bis, während",
            category = "Prepositions",
            iconEmoji = "📅",
            priority = ExamPriority.CORE_B1,
            summary = "Ordinal numbers (first, second, third...) are used for dates and rankings. Full date expressions use 'am + Ordinalzahl + -ten' (am dritten Mai). Temporal prepositions expand to include vor, nach, seit, bis, and ab.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Dates take the ending '-ten' after 'am': 'am ersten Mai', 'am dritten Oktober' (NOT: 'am erste Mai').",
                "A period after a number signifies an ordinal: '1.' = erste / ersten; '2.' = zweite / zweiten.",
                "seit vs. vor: 'seit' = started in the past and still ongoing (seit einem Jahr = for a year); 'vor' = completed in the past (vor einem Jahr = a year ago)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_14_dates_and_ordinals",
                    title = "Dates & Temporal Prepositions",
                    explanation = "How to say dates and use temporal prepositions.",
                    table = GrammarTable(
                        headers = listOf("Preposition", "Case", "Meaning", "Example"),
                        rows = listOf(
                            listOf("am (+ date)", "Dativ", "on (specific day)", "am 15. (fünfzehnten) Juni"),
                            listOf("vor", "Dativ", "ago / before", "vor zwei Wochen / vor dem Essen"),
                            listOf("nach", "Dativ", "after", "nach dem Kurs / nach der Arbeit"),
                            listOf("seit", "Dativ", "since / for (duration)", "seit einem Monat"),
                            listOf("bis", "Akkusativ", "until", "bis nächsten Montag"),
                            listOf("ab", "Dativ", "starting from", "ab nächster Woche")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Mein Geburtstag ist am vierzehnten September.",
                            highlightWord = "am vierzehnten September",
                            english = "My birthday is on the fourteenth of September.",
                            note = "'am' triggers the '-ten' ending on the ordinal number."
                        ),
                        GrammarExample(
                            german = "Ich habe vor zwei Jahren meine Ausbildung abgeschlossen.",
                            highlightWord = "vor zwei Jahren",
                            english = "I finished my apprenticeship two years ago.",
                            note = "'vor' + Dative plural: completed past event."
                        )
                    )
                )
            )
        ),

        // ---------------------------------------------------------------------
        // A2.15: POSITION OF "NICHT"
        // ---------------------------------------------------------------------
        GrammarGuideTopic(
            id = "topic_a2_15_position_nicht",
            number = 15,
            level = CefrLevel.A2,
            title = "Position of “nicht” (Sentence vs. Element)",
            germanTitle = "Stellung von „nicht“ (Satz- vs. Teilverneinung)",
            subtitle = "Whole Clause Negation vs. nicht ... sondern",
            category = "Sentence Structure",
            iconEmoji = "🎯",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Where does 'nicht' go? If negating the whole sentence/action, 'nicht' is placed near the end (before participles, separable prefixes, or prepositional phrases). If negating a specific element, 'nicht' is placed directly before that word, often followed by 'sondern' (but rather).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Negating a whole sentence: 'nicht' moves to the end, but stands BEFORE: separable prefix ('Ich stehe heute nicht auf'), Partizip II ('Ich habe nicht geschlafen'), and fixed prepositions ('Ich fahre nicht nach Berlin').",
                "Negating a specific element: 'nicht' stands directly in front of that word: 'Er kommt nicht heute, sondern morgen'.",
                "sondern vs. aber: Use 'sondern' only after a negative clause to replace the negated element with the correct one ('nicht A, sondern B')."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_a2_15_position_rules",
                    title = "Rules for Placing 'nicht'",
                    explanation = "Summary of position rules for 'nicht'.",
                    table = GrammarTable(
                        headers = listOf("Negation Scope", "Rule for 'nicht'", "Example Sentence"),
                        rows = listOf(
                            listOf("Whole sentence (simple)", "At the very end", "Ich verstehe dich nicht."),
                            listOf("Whole sentence (with prefix)", "Before separable prefix", "Ich mache das Fenster nicht auf."),
                            listOf("Whole sentence (with Partizip II)", "Before past participle", "Ich habe den Brief nicht abgeschickt."),
                            listOf("Whole sentence (preposition)", "Before prepositional phrase", "Wir fahren nicht nach Italien."),
                            listOf("Element negation (contrast)", "Directly before element + sondern", "Er studiert nicht Medizin, sondern Jura.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe gestern die E-Mail leider nicht gelesen.",
                            highlightWord = "nicht gelesen",
                            english = "Unfortunately I did not read the email yesterday.",
                            note = "'nicht' stands immediately before the past participle at the end."
                        ),
                        GrammarExample(
                            german = "Er kommt nicht heute, sondern erst morgen früh.",
                            highlightWord = "nicht heute, sondern",
                            english = "He is not coming today, but rather tomorrow morning.",
                            note = "Element negation: 'nicht' directly before the contrasted adverb 'heute'."
                        )
                    )
                )
            )
        )
    )
}
