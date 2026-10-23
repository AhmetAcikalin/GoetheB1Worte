package com.example.goetheb1worte.data.grammar.guide

import com.example.goetheb1worte.data.repository.GameType

object GrammarGuideData {

    val b1Topics: List<GrammarGuideTopic> = listOf(
        // =========================================================================
        // 1. CASES & NOUN DECLENSION
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_01_cases",
            number = 1,
            title = "Cases & Noun Declension",
            germanTitle = "Kasus & Nomen-Deklination",
            subtitle = "Genitiv, N-Deklination, Wechselpräpositionen & Time",
            category = "Cases & Nouns",
            iconEmoji = "⚖️",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Master all 4 German cases, especially the Genitiv newly required at B1, weak masculine N-Deklination, two-way prepositions (Wo vs. Wohin), and time prepositions.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Akkusativ vs. Dativ with Wechselpräpositionen: Ask 'Wohin?' (motion/destination = Akkusativ) or 'Wo?' (location/static = Dativ).",
                "N-Deklination trap: Weak masculine nouns (der Kunde, der Kollege, der Junge, der Nachbar) add -(e)n in Akk, Dat, and Gen.",
                "Genitiv masculine & neuter nouns take -(e)s ending (des Vaters, des Hauses), but feminine and plural nouns do NOT add -s (der Mutter, der Kinder)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_01_four_cases",
                    title = "The Four Cases & Article Declension",
                    explanation = "In German, nouns and articles decline across 4 cases: Nominativ (subject), Akkusativ (direct object), Dativ (indirect object / location), and Genitiv (possession / specific prepositions).",
                    table = GrammarTable(
                        headers = listOf("Case", "Masculine", "Feminine", "Neuter", "Plural"),
                        rows = listOf(
                            listOf("Nominativ (der/die/das)", "der / ein", "die / eine", "das / ein", "die / -"),
                            listOf("Akkusativ (den/die/das)", "den / einen", "die / eine", "das / ein", "die / -"),
                            listOf("Dativ (dem/der/dem/den+n)", "dem / einem", "der / einer", "dem / einem", "den / -n"),
                            listOf("Genitiv (des+s/der/des+s/der)", "des / eines (+s)", "der / einer", "des / eines (+s)", "der / -")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Der Lehrer erklärt den Studenten die Grammatik.",
                            highlightWord = "den Studenten",
                            english = "The teacher explains the grammar to the students.",
                            note = "Subject = Nom (Der Lehrer), Indirect Object = Dat (den Studenten), Direct Object = Akk (die Grammatik)."
                        ),
                        GrammarExample(
                            german = "Ich schenke meiner Schwester ein spannendes Buch.",
                            highlightWord = "meiner Schwester",
                            english = "I give my sister an exciting book.",
                            note = "Dativ feminine 'meiner Schwester', Akkusativ neuter 'ein Buch'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_02_genitiv",
                    title = "Genitiv (Possession & Relations)",
                    explanation = "Genitiv indicates ownership or belonging ('whose?'). Masculine and neuter singular nouns add -s (or -es for single-syllable nouns or endings in -s, -ß, -z, -x). Feminine and plural nouns do not change their noun ending.",
                    examples = listOf(
                        GrammarExample(
                            german = "Das ist das Auto meines Vaters.",
                            highlightWord = "meines Vaters",
                            english = "That is my father's car.",
                            note = "Masculine Genitive: 'des/meines' + noun ending '-s'."
                        ),
                        GrammarExample(
                            german = "Die Tasche meiner Mutter steht im Flur.",
                            highlightWord = "meiner Mutter",
                            english = "My mother's bag is in the hallway.",
                            note = "Feminine Genitive: 'der/meiner', no noun ending."
                        ),
                        GrammarExample(
                            german = "Die Zukunft des Kindes ist den Eltern wichtig.",
                            highlightWord = "des Kindes",
                            english = "The child's future is important to the parents.",
                            note = "Neuter Genitive: 'des' + noun ending '-es'."
                        )
                    ),
                    tip = "In spoken German, 'von + Dativ' is common, but in Goethe/telc B1 written exams, proper Genitiv is expected and scored higher."
                ),
                GrammarGuideSubtopic(
                    id = "sub_03_genitiv_prep",
                    title = "Genitiv Prepositions (wegen, während, trotz, anstatt)",
                    explanation = "At B1, four crucial prepositions govern the Genitiv: 'wegen' (because of), 'während' (during), 'trotz' (despite), and 'statt / anstatt' (instead of).",
                    examples = listOf(
                        GrammarExample(
                            german = "Trotz des schlechten Wetters kam er pünktlich.",
                            highlightWord = "Trotz des schlechten Wetters",
                            english = "Despite the bad weather, he arrived on time.",
                            note = "'trotz' + Genitiv masculine/neuter: des + -s."
                        ),
                        GrammarExample(
                            german = "Wegen eines schweren Unfalls war die Straße gesperrt.",
                            highlightWord = "Wegen eines schweren Unfalls",
                            english = "Because of a severe accident, the street was closed.",
                            note = "'wegen' + Genitiv."
                        ),
                        GrammarExample(
                            german = "Während der Sommerferien verreisen viele Familien.",
                            highlightWord = "Während der Sommerferien",
                            english = "During the summer vacation, many families travel.",
                            note = "'während' + Genitiv plural: der Ferien."
                        ),
                        GrammarExample(
                            german = "Anstatt eines Briefes schickte sie eine schnelle E-Mail.",
                            highlightWord = "Anstatt eines Briefes",
                            english = "Instead of a letter, she sent a quick email.",
                            note = "'statt / anstatt' + Genitiv."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_04_n_deklination",
                    title = "N-Deklination (Weak Masculine Nouns)",
                    explanation = "A specific group of masculine nouns takes -(e)n in all cases EXCEPT Nominativ Singular. This includes nouns ending in -e denoting people/animals (der Kollege, der Kunde, der Junge, der Nachbar, der Löwe) and foreign suffix words (-ent, -ist, -ant: der Student, der Polizist, der Praktikant).",
                    table = GrammarTable(
                        headers = listOf("Case", "Singular", "Plural"),
                        rows = listOf(
                            listOf("Nominativ", "der Kunde", "die Kunden"),
                            listOf("Akkusativ", "den Kunden", "die Kunden"),
                            listOf("Dativ", "dem Kunden", "den Kunden"),
                            listOf("Genitiv", "des Kunden", "der Kunden")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich habe den Kunden gestern ausführlich beraten.",
                            highlightWord = "den Kunden",
                            english = "I advised the client thoroughly yesterday.",
                            note = "Accusative singular adds -n: 'den Kunden'."
                        ),
                        GrammarExample(
                            german = "Sie spricht mit dem neuen Nachbarn.",
                            highlightWord = "dem neuen Nachbarn",
                            english = "She is talking to the new neighbor.",
                            note = "Dative singular adds -n: 'dem Nachbarn'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_05_wechselpraepositionen",
                    title = "Two-Way Prepositions (Wechselpräpositionen)",
                    explanation = "Nine prepositions (an, auf, hinter, in, neben, über, unter, vor, zwischen) take Akkusativ when describing motion towards a goal ('Wohin?'), and Dativ when describing a static position or location ('Wo?').",
                    table = GrammarTable(
                        headers = listOf("Action / Motion (Wohin? -> Akkusativ)", "Position / Location (Wo? -> Dativ)"),
                        rows = listOf(
                            listOf("legen (lay down): Ich lege das Buch auf den Tisch.", "liegen (lie): Das Buch liegt auf dem Tisch."),
                            listOf("stellen (put upright): Er stellt das Glas auf den Tisch.", "stehen (stand): Das Glas steht auf dem Tisch."),
                            listOf("setzen (seat): Sie setzt das Kind auf den Stuhl.", "sitzen (sit): Das Kind sitzt auf dem Stuhl."),
                            listOf("hängen (hang up): Ich hänge das Bild an die Wand.", "hängen (hang): Das Bild hängt an der Wand.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich lege das Buch auf den Tisch.",
                            highlightWord = "auf den Tisch",
                            english = "I put the book onto the table.",
                            note = "Direction (Wohin?) -> Akkusativ: 'den Tisch'."
                        ),
                        GrammarExample(
                            german = "Das Buch liegt schon auf dem Tisch.",
                            highlightWord = "auf dem Tisch",
                            english = "The book is already lying on the table.",
                            note = "Location (Wo?) -> Dativ: 'dem Tisch'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_06_fixed_prepositions",
                    title = "Fixed-Case Prepositions (Review)",
                    explanation = "Certain prepositions ALWAYS take Dativ, while others ALWAYS take Akkusativ, regardless of motion.",
                    table = GrammarTable(
                        headers = listOf("Dativ Only", "Akkusativ Only"),
                        rows = listOf(
                            listOf("aus, bei, mit, nach, seit, von, zu, gegenüber", "durch, für, gegen, ohne, um, bis")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Nach der Arbeit gehe ich zu meiner Schwester.",
                            highlightWord = "Nach der Arbeit / zu meiner Schwester",
                            english = "After work I am going to my sister's.",
                            note = "'nach' and 'zu' always require Dativ."
                        ),
                        GrammarExample(
                            german = "Ohne deinen Schlüssel kommen wir nicht durch die Tür.",
                            highlightWord = "Ohne deinen Schlüssel / durch die Tür",
                            english = "Without your key we cannot get through the door.",
                            note = "'ohne' and 'durch' always require Akkusativ."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_time_prepositions",
                    title = "Two-Way Prepositions with Time Expressions",
                    explanation = "When prepositions denote time rather than space, they take fixed cases: 'seit' + Dativ (started in past, still ongoing), 'vor' + Dativ (completed past time), and 'in' + Dativ (future time frame or duration).",
                    examples = listOf(
                        GrammarExample(
                            german = "Ich lerne seit zwei Jahren Deutsch.",
                            highlightWord = "seit zwei Jahren",
                            english = "I have been learning German for two years.",
                            note = "'seit' + Dativ plural (Jahre -> Jahren)."
                        ),
                        GrammarExample(
                            german = "Vor einem Monat habe ich meine Prüfung bestanden.",
                            highlightWord = "Vor einem Monat",
                            english = "A month ago I passed my exam.",
                            note = "'vor' + Dativ masculine: 'einem Monat'."
                        ),
                        GrammarExample(
                            german = "In einer Woche fahren wir in den Urlaub.",
                            highlightWord = "In einer Woche",
                            english = "In one week we are leaving for vacation.",
                            note = "'in' + Dativ feminine: 'einer Woche'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 2. VERBS AND FIXED PREPOSITIONS (REKTION)
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_02_rektion",
            number = 2,
            title = "Verbs & Fixed Prepositions",
            germanTitle = "Verben mit festen Präpositionen (Rektion)",
            subtitle = "Verb-Preposition Collocations, Reflexives & Da-/Wo-Compounds",
            category = "Verbs",
            iconEmoji = "🔗",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "In German, many verbs bind strictly to a specific preposition and case. Master these collocations, reflexive combinations, and pronominal adverbs (woran / daran).",
            targetGameType = GameType.VOCAB_QUIZ,
            examTraps = listOf(
                "Do not confuse 'sich freuen auf' (anticipating future: Ich freue mich auf den Urlaub) with 'sich freuen über' (happy about present/past: Ich freue mich über das Geschenk).",
                "Pronominal adverbs (woran, daran, worauf, darauf) are only for THINGS and ABSTRACT ACTIONS. For persons, use preposition + pronoun (Auf wen wartest du? Auf sie!).",
                "Remember the euphonic '-r-' in da(r)- and wo(r)- when the preposition begins with a vowel: da + r + auf = darauf, wo + r + an = woran."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_02_rektion_list",
                    title = "Key B1 Verbs with Prepositions",
                    explanation = "German requires memorizing the verb, preposition, and case as a single unit. Here are the highest-yield B1 verbs:",
                    table = GrammarTable(
                        headers = listOf("Verb + Preposition + Case", "Meaning", "Example"),
                        rows = listOf(
                            listOf("warten auf + Akk", "wait for", "Ich warte auf den Bus."),
                            listOf("denken an + Akk", "think of", "Denkst du an deinen Termin?"),
                            listOf("bitten um + Akk", "ask for / request", "Er bittet um Hilfe."),
                            listOf("sich interessieren für + Akk", "be interested in", "Sie interessiert sich für Kunst."),
                            listOf("teilnehmen an + Dat", "participate in", "Wir nehmen am Kurs teil."),
                            listOf("träumen von + Dat", "dream of", "Er träumt von einer Weltreise."),
                            listOf("gehören zu + Dat", "belong to", "Das gehört zu meinen Aufgaben."),
                            listOf("abhängen von + Dat", "depend on", "Das hängt vom Wetter ab.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Wir nehmen an der Konferenz teil.",
                            highlightWord = "an der Konferenz teil",
                            english = "We are participating in the conference.",
                            note = "'teilnehmen an' requires Dativ: an + der = an der."
                        ),
                        GrammarExample(
                            german = "Sie wartet seit einer halben Stunde auf den Arzt.",
                            highlightWord = "auf den Arzt",
                            english = "She has been waiting for the doctor for half an hour.",
                            note = "'warten auf' requires Akkusativ: auf den Arzt."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_02_reflexives",
                    title = "Reflexive Verbs with Prepositions",
                    explanation = "Many prepositional verbs are also reflexive. They require both a matching reflexive pronoun (mich, dich, sich, uns, euch, sich) and the fixed preposition.",
                    examples = listOf(
                        GrammarExample(
                            german = "Ich interessiere mich sehr für deutsche Geschichte.",
                            highlightWord = "interessiere mich sehr für",
                            english = "I am very interested in German history.",
                            note = "'sich interessieren für' + Akkusativ."
                        ),
                        GrammarExample(
                            german = "Er erinnert sich gern an seine Schulzeit.",
                            highlightWord = "erinnert sich gern an",
                            english = "He likes to remember his school days.",
                            note = "'sich erinnern an' + Akkusativ."
                        ),
                        GrammarExample(
                            german = "Wir freuen uns schon auf das Wochenende.",
                            highlightWord = "freuen uns schon auf",
                            english = "We are already looking forward to the weekend.",
                            note = "'sich freuen auf' + Akkusativ (future expectation)."
                        ),
                        GrammarExample(
                            german = "Sie ärgert sich über die Verspätung der Bahn.",
                            highlightWord = "ärgert sich über",
                            english = "She is annoyed about the train delay.",
                            note = "'sich ärgern über' + Akkusativ."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_02_da_wo_compounds",
                    title = "Da- & Wo-Compounds (Pronominal Adverbs)",
                    explanation = "When asking questions or referring back to things/actions, prepositions combine with 'wo(r)-' or 'da(r)-' instead of using pronoun phrases like 'für was' or 'auf es'.",
                    table = GrammarTable(
                        headers = listOf("Referring to Things / Actions", "Referring to People"),
                        rows = listOf(
                            listOf("Woran denkst du? — Ich denke daran.", "An wen denkst du? — An meine Schwester."),
                            listOf("Worauf freust du dich? — Auf den Urlaub / Darauf.", "Auf wen wartest du? — Auf meinen Freund."),
                            listOf("Wofür dankst du ihm? — Für das Geschenk / Dafür.", "Für wen ist das Geschenk? — Für meine Mutter.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Woran denkst du gerade? — Ich denke an die morgige Prüfung.",
                            highlightWord = "Woran / an die morgige Prüfung",
                            english = "What are you thinking about right now? — I am thinking about tomorrow's exam.",
                            note = "Thing -> 'Woran'. (Never say 'An was denkst du?')"
                        ),
                        GrammarExample(
                            german = "Hast du an das Ticket gedacht? — Ja, ich habe daran gedacht.",
                            highlightWord = "daran gedacht",
                            english = "Did you remember the ticket? — Yes, I thought about it.",
                            note = "'da + r + an = daran'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 3. ADJECTIVES
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_03_adjectives",
            number = 3,
            title = "Adjectives & Declension",
            germanTitle = "Adjektivdeklination & Steigerung",
            subtitle = "Weak, Mixed, and Strong Endings + Comparative & Superlative",
            category = "Adjectives",
            iconEmoji = "🎨",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Adjective endings are among the most frequently tested areas in B1 exams. Learn the 3 declension patterns (weak, mixed, strong) and irregular comparative/superlative forms.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Rule of thumb for Definite Articles (Weak): Only two possible endings exist: -e and -en! Nominative singular and feminine/neuter accusative take -e; ALL others take -en.",
                "In Dative plural, both the adjective AND the noun take -n (mit den netten Nachbarn).",
                "Remember irregular comparatives: gut → besser → am besten; viel → mehr → am meisten; gern → lieber → am liebsten; hoch → höher → am höchsten."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_03_endings_table",
                    title = "Adjective Endings Complete Matrix",
                    explanation = "Adjectives before a noun take endings depending on what article precedes them:",
                    table = GrammarTable(
                        headers = listOf("Case", "Masculine", "Feminine", "Neuter", "Plural"),
                        rows = listOf(
                            listOf("Nom (der/die/das)", "der neue Wagen (-e)", "die neue Tasche (-e)", "das neue Buch (-e)", "die neuen Bücher (-en)"),
                            listOf("Nom (ein/eine/ein)", "ein neuer Wagen (-er)", "eine neue Tasche (-e)", "ein neues Buch (-es)", "meine neuen Bücher (-en)"),
                            listOf("Nom (no article)", "neuer Wagen (-er)", "neue Tasche (-e)", "neues Buch (-es)", "neue Bücher (-e)"),
                            listOf("Akk (den/die/das)", "den neuen Wagen (-en)", "die neue Tasche (-e)", "das neue Buch (-e)", "die neuen Bücher (-en)"),
                            listOf("Akk (ein/eine/ein)", "einen neuen Wagen (-en)", "eine neue Tasche (-e)", "ein neues Buch (-es)", "meine neuen Bücher (-en)"),
                            listOf("Dat (all articles)", "-en (dem neuen)", "-en (der neuen)", "-en (dem neuen)", "-en (den neuen)")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Er trinkt gern heißen Kaffee mit frischer Milch.",
                            highlightWord = "heißen Kaffee / frischer Milch",
                            english = "He likes to drink hot coffee with fresh milk.",
                            note = "No article (strong): Akk masc = -en, Dat fem = -er."
                        ),
                        GrammarExample(
                            german = "Wir haben ein neues Auto gekauft.",
                            highlightWord = "ein neues Auto",
                            english = "We bought a new car.",
                            note = "Mixed declension: ein + neuter accusative = -es."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_03_comparative",
                    title = "Comparative & Superlative (Steigerung)",
                    explanation = "Comparatives add '-er' (+ als for comparison). Superlatives use 'am ...-sten' (adverbial) or definite article + '-ste / -sten' (attributive before noun). Short vowels often take an Umlaut (a→ä, o→ö, u→ü).",
                    table = GrammarTable(
                        headers = listOf("Positive", "Comparative (Komparativ)", "Superlative (Superlativ)"),
                        rows = listOf(
                            listOf("schnell (fast)", "schneller als (faster than)", "am schnellsten (fastest)"),
                            listOf("groß (tall/big)", "größer als (taller than)", "am größten (tallest)"),
                            listOf("alt (old)", "älter als (older than)", "am ältesten (oldest)"),
                            listOf("gut (good)", "besser als (better than)", "am besten (best)"),
                            listOf("viel (much)", "mehr als (more than)", "am meisten (most)"),
                            listOf("gern (gladly)", "lieber als (prefer to)", "am liebsten (favorite)"),
                            listOf("nah (near)", "näher als (nearer than)", "am nächsten (nearest)")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Mein Bruder ist zwei Jahre älter als ich.",
                            highlightWord = "älter als",
                            english = "My brother is two years older than I am.",
                            note = "Comparative uses 'als' for comparisons."
                        ),
                        GrammarExample(
                            german = "Das ist der beste Weg zum Bahnhof.",
                            highlightWord = "der beste Weg",
                            english = "That is the best way to the station.",
                            note = "Attributive superlative declined before noun."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 4. VERB TENSES
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_04_tenses",
            number = 4,
            title = "Verb Tenses",
            germanTitle = "Zeitformen & Verben",
            subtitle = "Perfekt, Präteritum, Plusquamperfekt, Futur I & als vs. wenn",
            category = "Tenses",
            iconEmoji = "⏳",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Master all 5 active tenses for the Goethe B1 exam: Perfekt (spoken past), Präteritum (written narrative), Plusquamperfekt (earlier past), Futur I, and the crucial distinction between 'als' and 'wenn'.",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Auxiliary sein in Perfekt: Verbs of movement (gehen, fahren, fliegen), change of state (einschlafen, sterben), and exceptions (bleiben, sein, passieren, geschehen) MUST take 'sein'!",
                "'Als' vs. 'Wenn' in the past: 'Als' is for a SINGLE past event or defined era (Als ich ein Kind war...). 'Wenn' is for REPEATED events (Immer wenn ich Zeit hatte...) or present/future conditions.",
                "In subordinate clauses with 'nachdem', the action is in Plusquamperfekt if the main clause is in Präteritum: 'Nachdem er gegessen hatte, ging er.'"
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_04_perfekt",
                    title = "Perfekt (haben vs. sein)",
                    explanation = "The Perfekt is the primary spoken past tense in German. It consists of auxiliary 'haben' or 'sein' in position 2, and the Partizip II at the end of the clause.",
                    table = GrammarTable(
                        headers = listOf("Takes 'haben'", "Takes 'sein'"),
                        rows = listOf(
                            listOf("Transitive verbs (with direct object: Ich habe ein Buch gelesen.)", "Movement from A to B (Ich bin nach Hause gefahren.)"),
                            listOf("Reflexive verbs (Ich habe mich gefreut.)", "Change of condition/state (Das Kind ist eingeschlafen.)"),
                            listOf("Modal verbs (Ich habe das nicht gewollt.)", "Crucial exceptions: bleiben (ist geblieben), passieren (ist passiert), sein (ist gewesen)")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Gestern sind wir wegen des Staus zu spät angekommen.",
                            highlightWord = "sind ... angekommen",
                            english = "Yesterday we arrived too late because of the traffic jam.",
                            note = "'ankommen' describes arrival (movement/state) -> auxiliary 'sein'."
                        ),
                        GrammarExample(
                            german = "Sie ist das ganze Wochenende zu Hause geblieben.",
                            highlightWord = "ist ... geblieben",
                            english = "She stayed at home the whole weekend.",
                            note = "'bleiben' is a core exception that requires 'sein'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_04_praeteritum",
                    title = "Präteritum (Narrative Past)",
                    explanation = "Used primarily in written texts, newspapers, reports, and stories. At B1, you must actively produce the Präteritum of modal verbs (musste, konnte, wollte, durfte, sollte), auxiliary verbs (war, hatte, wurde), and common core verbs (gab, wusste, ging, kam, sah).",
                    table = GrammarTable(
                        headers = listOf("Infinitive", "Präteritum (ich/er/sie)", "Präteritum (wir/sie)"),
                        rows = listOf(
                            listOf("sein (be)", "war", "waren"),
                            listOf("haben (have)", "hatte", "hatten"),
                            listOf("werden (become)", "wurde", "wurden"),
                            listOf("müssen (must)", "musste", "mussten"),
                            listOf("können (can)", "konnte", "konnten"),
                            listOf("wissen (know)", "wusste", "wussten"),
                            listOf("geben (es gibt)", "es gab", "—")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Früher mussten die Menschen viel härter arbeiten.",
                            highlightWord = "mussten ... arbeiten",
                            english = "In the past, people had to work much harder.",
                            note = "Modal verb Präteritum drops the Umlaut: müssen → musste."
                        ),
                        GrammarExample(
                            german = "Damals gab es noch keine Smartphones.",
                            highlightWord = "gab es",
                            english = "Back then there were no smartphones yet.",
                            note = "'es gibt' becomes 'es gab' in the Präteritum."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_04_plusquamperfekt",
                    title = "Plusquamperfekt (Past Perfect)",
                    explanation = "Expresses an action that occurred before another event in the past ('Vorvergangenheit'). Formed with 'hatte' or 'war' + Partizip II. Often paired with 'nachdem' or 'bevor'.",
                    examples = listOf(
                        GrammarExample(
                            german = "Nachdem er gefrühstückt hatte, verließ er das Haus.",
                            highlightWord = "gefrühstückt hatte ... verließ",
                            english = "After he had eaten breakfast, he left the house.",
                            note = "Breakfast happened first (Plusquamperfekt), then leaving (Präteritum)."
                        ),
                        GrammarExample(
                            german = "Sie hatte den Zug verpasst, deshalb kam sie zu spät.",
                            highlightWord = "hatte ... verpasst",
                            english = "She had missed the train, which is why she arrived late.",
                            note = "Earlier past action explaining the past result."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_04_futur1",
                    title = "Futur I (Future Tense & Intentions)",
                    explanation = "Formed with the conjugated present tense of 'werden' in Position 2 and the infinitive verb at the very end of the sentence. Used for future plans, promises, and predictions.",
                    examples = listOf(
                        GrammarExample(
                            german = "Ich werde dich morgen Nachmittag anrufen.",
                            highlightWord = "werde ... anrufen",
                            english = "I will call you tomorrow afternoon.",
                            note = "'werden' + infinitive at the end."
                        ),
                        GrammarExample(
                            german = "Morgen wird es im ganzen Land regnen.",
                            highlightWord = "wird ... regnen",
                            english = "Tomorrow it will rain across the entire country.",
                            note = "Weather prediction with Futur I."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_04_als_wenn",
                    title = "Temporal Conjunctions: 'als' vs. 'wenn'",
                    explanation = "A classic B1 exam question: use 'als' for a single completed event or defined time period in the past. Use 'wenn' for repeated past events ('immer wenn') and for present or future occurrences.",
                    examples = listOf(
                        GrammarExample(
                            german = "Als ich ein Kind war, verbrachte ich jeden Sommer am See.",
                            highlightWord = "Als ich ein Kind war",
                            english = "When I was a child, I spent every summer at the lake.",
                            note = "Defined past era -> 'als'."
                        ),
                        GrammarExample(
                            german = "Immer wenn er nach Berlin kam, besuchte er uns.",
                            highlightWord = "Immer wenn",
                            english = "Whenever he came to Berlin, he visited us.",
                            note = "Repeated past event -> 'immer wenn'."
                        ),
                        GrammarExample(
                            german = "Wenn das Wetter schön ist, gehen wir spazieren.",
                            highlightWord = "Wenn das Wetter schön ist",
                            english = "When the weather is nice, we go for a walk.",
                            note = "Present/future condition -> 'wenn'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 5. PASSIVE VOICE (VORGANGSPASSIV)
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_05_passive",
            number = 5,
            title = "Passive Voice (Vorgangspassiv)",
            germanTitle = "Das Vorgangspassiv",
            subtitle = "Präsens, Präteritum & Passive with Modal Verbs",
            category = "Passive",
            iconEmoji = "⚙️",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "The passive voice shifts focus from the person doing the action to the action itself. Essential in news, formal instructions, work reports, and B1 writing tasks.",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Do not confuse 'wurde' (past passive: Das Haus wurde gebaut) with 'würde' (Konjunktiv II: Ich würde ein Haus bauen).",
                "With modal verbs in passive, 'werden' remains in the infinitive at the sentence end: Modal + Partizip II + werden (Die Tür muss repariert werden).",
                "Agent: Use 'von + Dativ' for the active person/cause, and 'durch + Akkusativ' for the tool, means, or medium."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_05_praesens_passive",
                    title = "Passiv Präsens (werden + Partizip II)",
                    explanation = "Formed by conjugating 'werden' in the present tense (ich werde, du wirst, er wird, wir werden, ihr werdet, sie werden) in position 2, and placing the Partizip II at the end.",
                    examples = listOf(
                        GrammarExample(
                            german = "Die Tür wird heute repariert.",
                            highlightWord = "wird ... repariert",
                            english = "The door is being repaired today.",
                            note = "Singular: 'wird' + Partizip II."
                        ),
                        GrammarExample(
                            german = "In Deutschland werden viele Autos gebaut.",
                            highlightWord = "werden ... gebaut",
                            english = "Many cars are manufactured in Germany.",
                            note = "Plural: 'werden' + Partizip II."
                        ),
                        GrammarExample(
                            german = "Der Patient wird von der Ärztin untersucht.",
                            highlightWord = "von der Ärztin",
                            english = "The patient is being examined by the physician.",
                            note = "Agent introduced by 'von + Dativ'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_05_praeteritum_passive",
                    title = "Passiv Präteritum (wurde / wurden + Partizip II)",
                    explanation = "Used to describe past processes. Formed with the Präteritum of 'werden' (wurde / wurden) and the Partizip II at the end.",
                    examples = listOf(
                        GrammarExample(
                            german = "Das alte Gebäude wurde vor zwei Jahren renoviert.",
                            highlightWord = "wurde ... renoviert",
                            english = "The old building was renovated two years ago.",
                            note = "Singular past passive: 'wurde'."
                        ),
                        GrammarExample(
                            german = "Die Dokumente wurden gestern abgeschickt.",
                            highlightWord = "wurden ... abgeschickt",
                            english = "The documents were sent off yesterday.",
                            note = "Plural past passive: 'wurden'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_05_modal_passive",
                    title = "Passiv with Modal Verbs (muss ... Partizip II werden)",
                    explanation = "When necessity or permission is expressed in the passive, the conjugated modal verb takes position 2, and the verbal bracket ends with [Partizip II + werden].",
                    examples = listOf(
                        GrammarExample(
                            german = "Diese Rechnung muss sofort bezahlt werden.",
                            highlightWord = "muss ... bezahlt werden",
                            english = "This invoice must be paid immediately.",
                            note = "Modal 'muss' in Pos 2, 'bezahlt werden' at the end."
                        ),
                        GrammarExample(
                            german = "Das Anmeldeformular kann online ausgefüllt werden.",
                            highlightWord = "kann ... ausgefüllt werden",
                            english = "The registration form can be filled out online.",
                            note = "Possibility: 'kann' + Partizip II + werden."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 6. KONJUNKTIV II
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_06_konjunktiv2",
            number = 6,
            title = "Konjunktiv II",
            germanTitle = "Konjunktiv II (Höflichkeit & Irreales)",
            subtitle = "Polite Requests, Advice, Wishes & Unreal Conditions",
            category = "Konjunktiv II",
            iconEmoji = "🌟",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Used for polite communication in daily German, giving advice, making wishes, and expressing hypothetical situations ('What would happen if...').",
            targetGameType = GameType.VERB_TENSE_TRAINER,
            examTraps = listOf(
                "Use the direct forms for high-frequency verbs: hätte (haben), wäre (sein), wüsste (wissen), käme (kommen), ginge (gehen). Do NOT say 'ich würde haben'.",
                "For most other verbs, use 'würde + Infinitiv' (Ich würde gern verreisen).",
                "In conditional sentences (wenn...), if the 'wenn'-clause comes first, the main clause starts directly with the verb (Wenn ich Zeit hätte, käme ich sofort)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_06_polite",
                    title = "Polite Requests & Ordering",
                    explanation = "In formal and everyday situations, Konjunktiv II softens requests and expresses polite wishes.",
                    examples = listOf(
                        GrammarExample(
                            german = "Könnten Sie mir bitte helfen?",
                            highlightWord = "Könnten Sie",
                            english = "Could you please help me?",
                            note = "Polite request using 'könnten'."
                        ),
                        GrammarExample(
                            german = "Ich hätte gern eine Tasse Kaffee und ein Glas Wasser.",
                            highlightWord = "Ich hätte gern",
                            english = "I would like a cup of coffee and a glass of water.",
                            note = "Polite ordering using 'hätte gern'."
                        ),
                        GrammarExample(
                            german = "Würden Sie mir bitte das Salz reichen?",
                            highlightWord = "Würden Sie ... reichen",
                            english = "Would you please pass me the salt?",
                            note = "Polite request with 'würden + Infinitiv'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_06_advice",
                    title = "Giving Advice (sollte / solltest)",
                    explanation = "The Konjunktiv II of 'sollen' (sollte, solltest, sollten) is the standard German way to give advice and recommendations.",
                    examples = listOf(
                        GrammarExample(
                            german = "Du siehst müde aus. Du solltest früher schlafen gehen.",
                            highlightWord = "solltest ... gehen",
                            english = "You look tired. You should go to sleep earlier.",
                            note = "Advice using 'solltest'."
                        ),
                        GrammarExample(
                            german = "Sie sollten unbedingt einen Arzt aufsuchen.",
                            highlightWord = "sollten ... aufsuchen",
                            english = "You should definitely see a doctor.",
                            note = "Formal recommendation using 'sollten'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_06_unreal",
                    title = "Unreal Conditions & Wishes (Wenn-Sätze)",
                    explanation = "Expresses hypothetical situations that are not currently true.",
                    examples = listOf(
                        GrammarExample(
                            german = "Wenn ich mehr Geld hätte, würde ich ein Haus kaufen.",
                            highlightWord = "hätte ... würde ich ... kaufen",
                            english = "If I had more money, I would buy a house.",
                            note = "'hätte' in subclause (verb-final), 'würde ich' in main clause."
                        ),
                        GrammarExample(
                            german = "Wenn das Wetter schön wäre, gingen wir an den Strand.",
                            highlightWord = "wäre ... gingen wir",
                            english = "If the weather were nice, we would go to the beach.",
                            note = "'gingen' is the authentic strong form of 'gehen'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 7. SENTENCE STRUCTURE & WORD ORDER
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_07_sentence_structure",
            number = 7,
            title = "Sentence Structure & Word Order",
            germanTitle = "Satzbau & Konnektoren",
            subtitle = "Subordinate Clauses, Indirect Questions, Inversion & Connectors",
            category = "Sentence Structure",
            iconEmoji = "🧩",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Master verb positions in German: verb-final in Nebensätze, position 2 in Hauptsätze, inversion after sentence adverbs (deshalb, trotzdem), and coordinating conjunctions (ADUSO).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Contrast: 'weil / obwohl' (subordinate clause -> verb at the end) vs. 'deshalb / trotzdem' (sentence adverb -> inversion, verb in Position 2) vs. 'denn / aber' (ADUSO -> Position 0, normal order).",
                "Subordinate clause in front: When a Nebensatz comes first, the following main clause MUST start with the conjugated verb (Weil es regnet, BLEIBT er zu Hause).",
                "Indirect questions with 'ob' (yes/no) or W-words always push the verb to the end: 'Ich weiß nicht, wann der Zug abfährt.'"
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_07_nebensaetze",
                    title = "Subordinate Clauses (Verb-Final Kickers)",
                    explanation = "Conjunctions like weil, da, dass, obwohl, wenn, als, nachdem, bevor, bis, seitdem, sodass, and falls force the conjugated verb to the very end of the clause.",
                    examples = listOf(
                        GrammarExample(
                            german = "Er kommt heute nicht zur Arbeit, weil er krank ist.",
                            highlightWord = "weil er krank ist",
                            english = "He is not coming to work today because he is sick.",
                            note = "'ist' kicked to the end."
                        ),
                        GrammarExample(
                            german = "Obwohl sie viel gelernt hat, war die Prüfung schwer.",
                            highlightWord = "Obwohl sie viel gelernt hat, war",
                            english = "Although she studied a lot, the exam was difficult.",
                            note = "Subclause first -> main clause begins with verb 'war'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_indirect_questions",
                    title = "Indirect / Embedded Questions",
                    explanation = "Embedded questions function as subordinate clauses. Yes/No questions start with 'ob'. Information questions start with the question word (wann, wo, wie, warum). In both, the verb moves to the end.",
                    examples = listOf(
                        GrammarExample(
                            german = "Können Sie mir sagen, wann der nächste Bus abfährt?",
                            highlightWord = "wann der nächste Bus abfährt",
                            english = "Can you tell me when the next bus departs?",
                            note = "W-word + verb-final order."
                        ),
                        GrammarExample(
                            german = "Ich möchte gerne wissen, ob das Museum heute geöffnet ist.",
                            highlightWord = "ob das Museum heute geöffnet ist",
                            english = "I would like to know whether the museum is open today.",
                            note = "Yes/no question embedded with 'ob' + verb-final."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_inversion",
                    title = "Sentence Adverbs & Inversion (deshalb, trotzdem, sonst)",
                    explanation = "Connectors like deshalb (therefore), deswegen, daher, trotzdem (nevertheless), außerdem (furthermore), and sonst (otherwise) occupy Position 1 in the sentence. The conjugated verb immediately follows in Position 2, causing subject-verb inversion.",
                    table = GrammarTable(
                        headers = listOf("Position 1 (Connector)", "Position 2 (Verb)", "Position 3 (Subject)", "Remainder"),
                        rows = listOf(
                            listOf("Deshalb", "lerne", "ich", "täglich Deutsch."),
                            listOf("Trotzdem", "geht", "er", "ohne Regenschirm raus."),
                            listOf("Sonst", "verpassen", "wir", "den Flug.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Es regnet stark. Trotzdem geht er joggen.",
                            highlightWord = "Trotzdem geht er",
                            english = "It is raining heavily. Nevertheless he goes jogging.",
                            note = "Inversion: Trotzdem (1) + geht (2) + er (3)."
                        ),
                        GrammarExample(
                            german = "Beeil dich, sonst kommen wir zu spät.",
                            highlightWord = "sonst kommen wir",
                            english = "Hurry up, otherwise we will be late.",
                            note = "Inversion: sonst (1) + kommen (2) + wir (3)."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_aduso",
                    title = "Coordinating Conjunctions: Position 0 (ADUSO)",
                    explanation = "Five coordinating conjunctions occupy 'Position 0' and do NOT change word order: Aber (but), Denn (because), Und (and), Sondern (rather/but), Oder (or). Normal Subject (Pos 1) + Verb (Pos 2) order follows.",
                    examples = listOf(
                        GrammarExample(
                            german = "Er lernt fleißig, denn er möchte die Prüfung bestehen.",
                            highlightWord = "denn er möchte",
                            english = "He is studying hard, for he wants to pass the exam.",
                            note = "Position 0 (denn) + Pos 1 (er) + Pos 2 (möchte)."
                        ),
                        GrammarExample(
                            german = "Wir fahren nicht nach Spanien, sondern wir bleiben in Deutschland.",
                            highlightWord = "sondern wir bleiben",
                            english = "We are not traveling to Spain, but rather staying in Germany.",
                            note = "'sondern' corrects a preceding negation (nicht)."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_doppelkonnektoren",
                    title = "Two-Part Connectors (Doppelkonnektoren)",
                    explanation = "Two-part connectors link ideas with special rhetorical relationships. High-frequency in B1 writing and reading tasks.",
                    table = GrammarTable(
                        headers = listOf("Connector", "Function", "Example"),
                        rows = listOf(
                            listOf("nicht nur ... sondern auch", "Positive addition (not only... but also)", "Er spricht nicht nur Deutsch, sondern auch Spanisch."),
                            listOf("sowohl ... als auch", "Equal addition (both... and)", "Sie mag sowohl Kaffee als auch Tee."),
                            listOf("weder ... noch", "Double negation (neither... nor)", "Er trinkt weder Alkohol noch raucht er."),
                            listOf("entweder ... oder", "Alternative (either... or)", "Wir fahren entweder mit dem Zug oder mit dem Auto."),
                            listOf("zwar ... aber", "Concession (admittedly... but)", "Die Wohnung ist zwar klein, aber sehr hell."),
                            listOf("je ... desto / umso", "Proportional (the more... the more)", "Je mehr du übst, desto besser wirst du.")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Er spricht nicht nur fließend Deutsch, sondern auch Englisch.",
                            highlightWord = "nicht nur ... sondern auch",
                            english = "He speaks not only fluent German, but also English.",
                            note = "Two-part connector for addition."
                        ),
                        GrammarExample(
                            german = "Je mehr du übst, desto besser sprichst du.",
                            highlightWord = "Je mehr ... desto besser",
                            english = "The more you practice, the better you speak.",
                            note = "'je' has verb-final order; 'desto' has verb in position 2."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_07_nicht_position",
                    title = "Position of 'nicht' (Negation)",
                    explanation = "Negating the whole sentence: 'nicht' stands near the end, just before the verbal bracket (before Partizip II, Infinitiv, separable prefix). Negating a specific element: 'nicht' stands directly before that element.",
                    examples = listOf(
                        GrammarExample(
                            german = "Er ruft mich heute Abend nicht an.",
                            highlightWord = "nicht an",
                            english = "He is not calling me tonight.",
                            note = "Sentence negation: 'nicht' right before separable prefix."
                        ),
                        GrammarExample(
                            german = "Er kommt nicht heute, sondern erst morgen.",
                            highlightWord = "nicht heute",
                            english = "He is coming not today, but tomorrow.",
                            note = "Element negation: 'nicht' directly before 'heute'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 8. INFINITIVE & PURPOSE CLAUSES
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_08_infinitives",
            number = 8,
            title = "Infinitive & Purpose Clauses",
            germanTitle = "Infinitiv- & Finalsätze",
            subtitle = "zu + Infinitiv, um...zu vs. damit, ohne...zu & statt...zu",
            category = "Infinitives",
            iconEmoji = "🎯",
            priority = ExamPriority.CORE_B1,
            summary = "Express intentions, goals, and conditions without repeating the subject. Master the crucial distinction between 'um...zu' (same subject) and 'damit' (different subjects).",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Classic B1 Rule: 'um...zu' can ONLY be used when the subject of the main clause and the purpose clause are IDENTICAL. If subjects differ, you MUST use 'damit'!",
                "Separable verbs: 'zu' is inserted BETWEEN the prefix and the verb stem: ein-zu-kaufen, an-zu-rufen, auf-zu-stehen.",
                "Do NOT use 'zu' with modal verbs (können, müssen, dürfen, wollen, sollen, mögen) or verbs of perception (sehen, hören)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_08_zu_infinitive",
                    title = "Infinitiv with 'zu'",
                    explanation = "Used after specific verbs (hoffen, planen, versuchen, vorhaben, versprechen), expressions with 'Es ist + Adjektiv' (Es ist wichtig/schön/leicht), and noun expressions with 'haben' (Lust/Zeit/Angst haben).",
                    examples = listOf(
                        GrammarExample(
                            german = "Es ist wichtig, jeden Tag Vokabeln zu wiederholen.",
                            highlightWord = "zu wiederholen",
                            english = "It is important to review vocabulary every day.",
                            note = "'Es ist wichtig' triggers 'zu + Infinitiv'."
                        ),
                        GrammarExample(
                            german = "Ich habe keine Zeit, heute Abend ins Kino zu gehen.",
                            highlightWord = "zu gehen",
                            english = "I have no time to go to the cinema tonight.",
                            note = "'Zeit haben' triggers 'zu + Infinitiv'."
                        ),
                        GrammarExample(
                            german = "Vergiss bitte nicht, Milch einzukaufen.",
                            highlightWord = "einzukaufen",
                            english = "Please don't forget to buy milk.",
                            note = "Separable verb: 'ein-zu-kaufen'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_08_um_zu_vs_damit",
                    title = "Purpose: 'um... zu' vs. 'damit'",
                    explanation = "Both express the purpose or goal ('in order to' / 'so that'). 'um... zu' is used when the subjects of both clauses are the SAME. 'damit' is used when the subjects are DIFFERENT (or can also be used with same subjects as a full clause with verb at the end).",
                    examples = listOf(
                        GrammarExample(
                            german = "Er spart Geld, um sich ein neues Auto zu kaufen.",
                            highlightWord = "um ... zu kaufen",
                            english = "He saves money in order to buy a new car.",
                            note = "Same subject (Er spart, er kauft) -> 'um... zu'."
                        ),
                        GrammarExample(
                            german = "Sie spricht langsam, damit die Schüler alles verstehen.",
                            highlightWord = "damit die Schüler ... verstehen",
                            english = "She speaks slowly so that the students understand everything.",
                            note = "Different subjects (Sie spricht, Schüler verstehen) -> 'damit' + verb-final."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_08_ohne_statt_zu",
                    title = "Manner & Contrast: 'ohne... zu' & '(an)statt... zu'",
                    explanation = "Infinitives with 'ohne... zu' express that an expected action did not occur ('without doing'). 'statt... zu / anstatt... zu' expresses that an alternative action was taken ('instead of doing').",
                    examples = listOf(
                        GrammarExample(
                            german = "Er ging aus dem Haus, ohne sich zu verabschieden.",
                            highlightWord = "ohne sich zu verabschieden",
                            english = "He left the house without saying goodbye.",
                            note = "'ohne ... zu' + infinitive."
                        ),
                        GrammarExample(
                            german = "Sie sah fern, anstatt für die Prüfung zu lernen.",
                            highlightWord = "anstatt ... zu lernen",
                            english = "She watched TV instead of studying for the exam.",
                            note = "'anstatt ... zu' + infinitive."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 9. RELATIVE CLAUSES
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_09_relatives",
            number = 9,
            title = "Relative Clauses",
            germanTitle = "Relativsätze",
            subtitle = "Relative Pronouns in all cases, Prepositions & dessen/deren",
            category = "Relative Clauses",
            iconEmoji = "🔍",
            priority = ExamPriority.CORE_B1,
            summary = "Relative clauses provide extra detail about a noun without starting a new sentence. The pronoun's gender matches the noun, but its case matches its role in the subclause.",
            targetGameType = GameType.SATZBAU_TRAINER,
            examTraps = listOf(
                "Watch out for Dativ Plural: The relative pronoun is 'denen' (NOT 'den'!). Example: Die Freunde, mit DENEN ich reise.",
                "Genitiv relative pronouns: 'dessen' for masculine and neuter, 'deren' for feminine and plural. (Der Mann, dessen Auto... / Die Frau, deren Tasche...).",
                "Relative clauses always send the conjugated verb to the end of the clause."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_09_pronouns_table",
                    title = "Relative Pronouns Table",
                    explanation = "Relative pronouns match the gender (masc/fem/neut) and number (singular/plural) of the referenced noun, while the case is determined by the grammatical function inside the relative clause.",
                    table = GrammarTable(
                        headers = listOf("Case", "Masculine", "Feminine", "Neuter", "Plural"),
                        rows = listOf(
                            listOf("Nominativ", "der", "die", "das", "die"),
                            listOf("Akkusativ", "den", "die", "das", "die"),
                            listOf("Dativ", "dem", "der", "dem", "denen"),
                            listOf("Genitiv", "dessen", "deren", "dessen", "deren")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Das ist der Mann, der nebenan wohnt.",
                            highlightWord = "der nebenan wohnt",
                            english = "That is the man who lives next door.",
                            note = "Referenced noun: der Mann (masc). Subject in subclause -> Nominativ masc: 'der'."
                        ),
                        GrammarExample(
                            german = "Hier sind die Kollegen, denen ich geholfen habe.",
                            highlightWord = "denen ich geholfen habe",
                            english = "Here are the colleagues whom I helped.",
                            note = "Dative plural relative pronoun is 'denen'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_09_with_prepositions",
                    title = "Relative Clauses with Prepositions",
                    explanation = "If the verb in the relative clause requires a preposition, that preposition stands directly before the relative pronoun and determines its case.",
                    examples = listOf(
                        GrammarExample(
                            german = "Der Kollege, mit dem ich das Projekt mache, ist sehr nett.",
                            highlightWord = "mit dem",
                            english = "The colleague with whom I am doing the project is very nice.",
                            note = "'mit' requires Dativ masculine: 'dem'."
                        ),
                        GrammarExample(
                            german = "Die Stadt, in der ich aufgewachsen bin, liegt im Süden.",
                            highlightWord = "in der",
                            english = "The city in which I grew up lies in the south.",
                            note = "'in' (location = Dativ) feminine: 'der'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_09_genitiv_relatives",
                    title = "Genitiv Relative Pronouns (dessen / deren)",
                    explanation = "Replaces possessive relationships ('whose'). Masculine and neuter nouns use 'dessen'. Feminine and plural nouns use 'deren'.",
                    examples = listOf(
                        GrammarExample(
                            german = "Das ist der Arzt, dessen Praxis im Zentrum liegt.",
                            highlightWord = "dessen Praxis",
                            english = "That is the doctor whose practice is located in the center.",
                            note = "Masculine reference: 'dessen'."
                        ),
                        GrammarExample(
                            german = "Die Frau, deren Tasche gestohlen wurde, hat die Polizei gerufen.",
                            highlightWord = "deren Tasche",
                            english = "The woman whose bag was stolen called the police.",
                            note = "Feminine reference: 'deren'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 10. PRONOUNS
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_10_pronouns",
            number = 10,
            title = "Pronouns",
            germanTitle = "Pronomen",
            subtitle = "Reflexive, Indefinite & Possessive Pronoun Paradigms",
            category = "Pronouns",
            iconEmoji = "👤",
            priority = ExamPriority.REVIEW_A2,
            summary = "Review the full paradigm of reflexive pronouns (Akkusativ vs. Dativ distinction), indefinite pronouns (man, jemand, niemand), and declined possessive pronouns across all cases.",
            targetGameType = GameType.KASUS_TRAINER,
            examTraps = listOf(
                "Reflexive Pronouns: Akkusativ (mich, dich, sich...) is used when there is no direct object. Dativ (mir, dir, sich...) is used when a specific body part or direct object is mentioned: 'Ich wasche MICH' vs. 'Ich wasche MIR die Hände'.",
                "'Jemand' and 'niemand' can decline in formal German: Akk 'jemanden/niemanden', Dat 'jemandem/niemandem'.",
                "Do not confuse the pronoun 'man' (people in general: Man darf hier nicht parken) with the masculine noun 'der Mann' (the man)."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_10_reflexives",
                    title = "Reflexive Pronouns: Akkusativ vs. Dativ",
                    explanation = "Reflexive pronouns refer back to the subject. In the 1st and 2nd person singular, there is a distinct Dativ form (mir, dir). All other forms (sich, uns, euch, sich) are identical in both cases.",
                    table = GrammarTable(
                        headers = listOf("Person", "Akkusativ", "Dativ"),
                        rows = listOf(
                            listOf("ich", "mich (Ich freue mich.)", "mir (Ich putze mir die Zähne.)"),
                            listOf("du", "dich (Du rasierst dich.)", "dir (Du kaufst dir ein Buch.)"),
                            listOf("er / sie / es", "sich", "sich"),
                            listOf("wir", "uns", "uns"),
                            listOf("ihr", "euch", "euch"),
                            listOf("sie / Sie", "sich", "sich")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich wasche mich jeden Morgen kalt.",
                            highlightWord = "wasche mich",
                            english = "I wash myself cold every morning.",
                            note = "No separate object -> Akkusativ 'mich'."
                        ),
                        GrammarExample(
                            german = "Ich wasche mir gründlich die Hände.",
                            highlightWord = "wasche mir die Hände",
                            english = "I wash my hands thoroughly.",
                            note = "Direct object 'die Hände' present -> reflexive in Dativ 'mir'."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_10_indefinites",
                    title = "Indefinite Pronouns (man, jemand, niemand, alle)",
                    explanation = "Indefinite pronouns refer to unspecified people or things.",
                    examples = listOf(
                        GrammarExample(
                            german = "Hier darf man nicht laut telefonieren.",
                            highlightWord = "man",
                            english = "One is not allowed to make loud phone calls here.",
                            note = "'man' takes 3rd person singular verb form."
                        ),
                        GrammarExample(
                            german = "Hat jemand meinen Schlüsselbund gesehen?",
                            highlightWord = "jemand",
                            english = "Has anybody seen my key ring?",
                            note = "'jemand' = someone / anybody."
                        ),
                        GrammarExample(
                            german = "Niemand wusste die Antwort auf diese Frage.",
                            highlightWord = "Niemand",
                            english = "Nobody knew the answer to this question.",
                            note = "'niemand' = nobody."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_10_possessives",
                    title = "Possessive Pronouns (Declined)",
                    explanation = "Possessive adjectives (mein, dein, sein, ihr, unser, euer, ihr, Ihr) decline like indefinite articles 'ein' before a noun, or take full definite article endings when replacing a noun standing alone.",
                    examples = listOf(
                        GrammarExample(
                            german = "Ist das dein Fahrrad? — Ja, das ist meins.",
                            highlightWord = "meins",
                            english = "Is that your bicycle? — Yes, that is mine.",
                            note = "Standalone pronoun: replaces das Fahrrad -> 'meins / meines'."
                        ),
                        GrammarExample(
                            german = "Er fährt mit seinem neuen Auto zur Arbeit.",
                            highlightWord = "seinem neuen Auto",
                            english = "He drives to work with his new car.",
                            note = "Dativ neuter possessive: 'mit seinem'."
                        )
                    )
                )
            )
        ),

        // =========================================================================
        // 11. EXAM STRATEGY & PRIORITY CHECKLIST (TELC & GOETHE B1)
        // =========================================================================
        GrammarGuideTopic(
            id = "topic_11_exam_strategy",
            number = 11,
            title = "B1 Exam Strategy & Priorities",
            germanTitle = "B1 Prüfungsstrategie & Checkliste",
            subtitle = "Highest Frequency Topics, Common Traps & Telc/Goethe Recommendations",
            category = "Exam Strategy",
            iconEmoji = "🏆",
            priority = ExamPriority.HIGH_FREQUENCY,
            summary = "Strategic guidance from certified B1 examiners: which grammar topics carry the most points, what is commonly under-practiced, and what to safely skip.",
            targetGameType = null,
            examTraps = listOf(
                "In the B1 letter/email writing task, examiners specifically award points for using two-part connectors (nicht nur... sondern auch), Konjunktiv II (Könnten Sie bitte...), and correct Nebensatz word order.",
                "Do NOT waste study hours on Konjunktiv I (reported speech) or extended participle constructions (Partizipialattribute); these are B2/C1 level and are NOT tested at B1.",
                "Always check your adjective endings in the writing section during the final 5-minute proofreading pass."
            ),
            subtopics = listOf(
                GrammarGuideSubtopic(
                    id = "sub_11_high_freq",
                    title = "Highest Exam Frequency (Top Priority)",
                    explanation = "These grammar items appear in nearly every B1 Sprachbausteine / reading test and carry maximum weight in the writing section:",
                    table = GrammarTable(
                        headers = listOf("Topic", "Exam Appearance", "Study Focus"),
                        rows = listOf(
                            listOf("Wechselpräpositionen", "Sprachbausteine & Reading", "Wo (Dativ) vs. Wohin (Akkusativ) pairs"),
                            listOf("Adjective Endings", "Sprachbausteine & Writing", "Nom masc (-er), Akk masc (-en), Dat (-en)"),
                            listOf("Präteritum of Modals & Verbs", "Reading & Writing", "musste, konnte, wollte, war, hatte, gab"),
                            listOf("Konjunktiv II", "Writing (formal email) & Speaking", "Höfliche Bitten: Könnten Sie, Ich hätte gern"),
                            listOf("Vorgangspassiv", "Sprachbausteine & Reading", "wird/wurde + Partizip II, modal passive"),
                            listOf("Subordinate Word Order", "Sprachbausteine & Writing", "weil, dass, obwohl, wenn, als (verb-final)"),
                            listOf("Two-Part Connectors", "Writing & Speaking", "nicht nur... sondern auch, je... desto")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Ich wende mich an Sie, weil ich eine Frage zu Ihrem Angebot habe.",
                            highlightWord = "weil ich ... habe",
                            english = "I am turning to you because I have a question regarding your offer.",
                            note = "Standard formal B1 letter opening demonstrating 'sich wenden an' + 'weil' verb-final."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_11_under_practiced",
                    title = "Commonly Under-Practiced (Where points are lost)",
                    explanation = "Students often lose easy marks on these specific items because they are neglected during prep:",
                    table = GrammarTable(
                        headers = listOf("Neglected Topic", "Why It Matters", "Quick Rule"),
                        rows = listOf(
                            listOf("Verb + Fixed Preposition (Rektion)", "Very common in Telc Part 1 & 2 gap-fills", "warten auf, teilnehmen an, denken an, bitten um"),
                            listOf("Da- & Wo-Compounds", "Distinguishes B1 from A2 writing", "Woran denkst du? / Ich freue mich darauf."),
                            listOf("Indirect Questions with 'ob'", "Formal request writing", "Ich möchte fragen, ob der Termin möglich ist."),
                            listOf("N-Deklination", "Frequent cloze trap", "den Kunden, dem Nachbarn, des Kollegen")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Könnten Sie mir bitte mitteilen, ob das Zimmer noch frei ist?",
                            highlightWord = "ob das Zimmer noch frei ist",
                            english = "Could you please inform me whether the room is still available?",
                            note = "High-scoring formal email formula combining Konjunktiv II + indirect 'ob' clause."
                        )
                    )
                ),
                GrammarGuideSubtopic(
                    id = "sub_11_borderline",
                    title = "Borderline B1/B2 vs. NOT B1",
                    explanation = "Avoid over-investing time in topics outside the official B1 catalogue:",
                    table = GrammarTable(
                        headers = listOf("Grammar Structure", "B1 Status", "Recommendation"),
                        rows = listOf(
                            listOf("Genitiv Relative Pronouns (dessen/deren)", "Borderline B1/B2 (present in some telc tests)", "Recognize for reading; don't stress writing"),
                            listOf("Extended Genitiv across all nouns", "Borderline B1/B2", "Focus on the 4 prepositions: wegen, während, trotz, statt"),
                            listOf("Konjunktiv I (Reported Speech: er habe gesagt)", "NOT B1 (B2/C1 only)", "Do NOT study for B1! Use 'Er hat gesagt, dass...'"),
                            listOf("Partizipialattribute (das gelesene Buch)", "NOT B1 (B2/C1 only)", "Do NOT study for B1! Use relative clauses instead")
                        )
                    ),
                    examples = listOf(
                        GrammarExample(
                            german = "Er hat mir gesagt, dass er morgen nicht kommen kann.",
                            highlightWord = "dass er ... kann",
                            english = "He told me that he cannot come tomorrow.",
                            note = "Correct B1 way to report speech (using 'dass' instead of Konjunktiv I)."
                        )
                    )
                )
            )
        )
    )
 
    val a1Topics: List<GrammarGuideTopic> get() = A1GrammarData.topics
    val a2Topics: List<GrammarGuideTopic> get() = A2GrammarData.topics

    val topics: List<GrammarGuideTopic> by lazy {
        a1Topics + a2Topics + b1Topics
    }

    fun getTopicsByLevel(level: CefrLevel?): List<GrammarGuideTopic> {
        return if (level == null) topics else topics.filter { it.level == level }
    }

    fun getTopicById(id: String): GrammarGuideTopic? {
        return topics.firstOrNull { it.id == id }
    }

    fun search(query: String, level: CefrLevel? = null): List<GrammarGuideTopic> {
        val pool = getTopicsByLevel(level)
        val q = query.trim().lowercase()
        if (q.isBlank()) return pool
        return pool.filter { topic ->
            topic.title.lowercase().contains(q) ||
            topic.germanTitle.lowercase().contains(q) ||
            topic.subtitle.lowercase().contains(q) ||
            topic.category.lowercase().contains(q) ||
            topic.summary.lowercase().contains(q) ||
            topic.level.code.lowercase().contains(q) ||
            topic.level.title.lowercase().contains(q) ||
            topic.examTraps.any { it.lowercase().contains(q) } ||
            topic.subtopics.any { sub ->
                sub.title.lowercase().contains(q) ||
                sub.explanation.lowercase().contains(q) ||
                sub.examples.any { ex ->
                    ex.german.lowercase().contains(q) ||
                    ex.english.lowercase().contains(q) ||
                    ex.highlightWord.lowercase().contains(q)
                }
            }
        }
    }
}
