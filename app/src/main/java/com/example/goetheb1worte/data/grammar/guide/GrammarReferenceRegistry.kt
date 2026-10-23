package com.example.goetheb1worte.data.grammar.guide

object GrammarReferenceRegistry {

    data class TopicReference(
        val rules: List<String>,
        val tips: List<String>,
        val exceptions: List<String>
    )

    private val referenceMap: Map<String, TopicReference> = mapOf(
        // ========================== A1 TOPICS ==========================
        "topic_a1_01_pronouns" to TopicReference(
            rules = listOf(
                "Personal pronouns replace previously mentioned nouns and serve as the subject (Nominativ) of a clause.",
                "In German, objects replace nouns according to grammatical gender: der Tisch -> er, die Tasche -> sie, das Buch -> es.",
                "The conjugated finite verb ending must agree with the subject pronoun (ich -e, du -st, er/sie/es -t, wir -en, ihr -t, sie/Sie -en)."
            ),
            tips = listOf(
                "Always capitalize 'Sie' for formal 'you' (both singular and plural). It always takes the 3rd person plural verb ending '-en'.",
                "When speaking of things or objects, never default to 'es' in English — check the article: 'Wo ist der Schlüssel? Er liegt hier.'"
            ),
            exceptions = listOf(
                "The impersonal pronoun 'man' (one/people) takes 3rd person singular verb ending: 'Hier spricht man Deutsch'.",
                "'ihr' can be plural informal 'you all' (ihr lernt) OR the possessive root for 'her/their'."
            )
        ),
        "topic_a1_02_present_verbs" to TopicReference(
            rules = listOf(
                "Regular present tense endings: ich -e, du -st, er/sie/es -t, wir -en, ihr -t, sie/Sie -en.",
                "Strong verbs with vowel shift only change the stem vowel in 2nd person (du) and 3rd person (er/sie/es) singular.",
                "Common shifts: e -> i (helfen -> du hilfst), e -> ie (sehen -> du siehst), a -> ä (fahren -> du fährst), au -> äu (laufen -> du läufst)."
            ),
            tips = listOf(
                "1st person ('ich') and plural persons ('wir', 'ihr', 'sie/Sie') NEVER undergo vowel shifts in the present tense.",
                "Verb stems ending in -d or -t (arbeiten, finden) insert an '-e-' before endings for easier pronunciation: du arbeitest, er arbeitet."
            ),
            exceptions = listOf(
                "'wissen' is a preterite-present verb: ich weiß, du weißt, er weiß (no ending for ich & er/sie/es!).",
                "'nehmen' drops the 'h' and doubles 'm': ich nehme, du nimmst, er nimmt."
            )
        ),
        "topic_a1_03_word_order" to TopicReference(
            rules = listOf(
                "The Golden Rule of German main clauses: The conjugated finite verb is ALWAYS in Position 2.",
                "In W-Questions (W-Fragen), the question word is Position 1, and the finite verb is in Position 2.",
                "In Yes/No Questions (Ja/Nein-Fragen), the finite verb moves to Position 1: 'Kommst du heute?'."
            ),
            tips = listOf(
                "Position 1 can be an adverb or time phrase: 'Heute (1) lerne (2) ich (3) Deutsch'. The subject simply moves to Position 3 (Inversion).",
                "TeKaMoLo guideline: Temporal (When?), Kausal (Why?), Modal (How?), Lokativ (Where?)."
            ),
            exceptions = listOf(
                "Coordinating conjunctions ADUSO (aber, denn, und, sondern, oder) sit in Position 0 and do not count towards Position 1."
            )
        ),
        "topic_a1_04_nom_akk" to TopicReference(
            rules = listOf(
                "Nominativ is the subject (Wer oder was? / Who or what is doing the action?).",
                "Akkusativ is the direct object (Wen oder was? / Who or what is being acted upon?).",
                "ONLY masculine articles change in Akkusativ: der -> den, ein -> einen, kein -> keinen. Feminine, neuter, and plural stay identical to Nominativ."
            ),
            tips = listOf(
                "Whenever an action is done to a masculine person or object, add '-en': 'Ich sehe den Mann', 'Ich habe einen Hund'.",
                "Most transitive verbs require an Akkusativ object: haben, brauchen, suchen, kaufen, finden, trinken, essen."
            ),
            exceptions = listOf(
                "Verbs 'sein', 'werden', 'bleiben' take a predicative Nominativ on both sides: 'Er ist ein guter Arzt' (NOT einen!)."
            )
        ),
        "topic_a1_05_negation" to TopicReference(
            rules = listOf(
                "Use 'kein' (keine, keinen, etc.) to negate indefinite nouns (nouns that would take 'ein/eine' or zero article).",
                "Use 'nicht' to negate verbs, adjectives, adverbs, proper names, and definite nouns with 'der/die/das'.",
                "To negate the entire sentence, place 'nicht' at the end (or before the second verbal element). To negate a specific word, place 'nicht' directly before it."
            ),
            tips = listOf(
                "Test: If you can replace the sentence with 'no' in English ('no bread', 'no car'), use 'kein'. If you say 'not' ('not expensive', 'not coming'), use 'nicht'.",
                "Contrastive negation uses 'nicht ... sondern ...': 'Ich trinke nicht Tee, sondern Kaffee.'"
            ),
            exceptions = listOf(
                "Fixed verb-noun phrases (Klavier spielen, Auto fahren, Sport treiben) place 'nicht' before the noun: 'Ich kann nicht Auto fahren'."
            )
        ),

        // ========================== A2 TOPICS ==========================
        "topic_a2_01_dativ_case" to TopicReference(
            rules = listOf(
                "Dativ is the indirect object (Wem? / To or for whom?).",
                "Articles in Dativ: dem (masculine), der (feminine), dem (neuter), den + -n (plural).",
                "Personal pronouns in Dativ: mir, dir, ihm, ihr, ihm, uns, euch, ihnen/Ihnen."
            ),
            tips = listOf(
                "Remember the Dativ verb chant: helfen, danken, gefallen, gehören, gratulieren, schmecken, passen.",
                "If both Dativ (person) and Akkusativ (thing) objects are nouns, Dativ comes first: 'Ich schenke meiner Mutter ein Buch'."
            ),
            exceptions = listOf(
                "If the Akkusativ object is a pronoun (es, ihn, sie), it moves ahead of the Dativ noun: 'Ich gebe es meinem Bruder'.",
                "Plural nouns in Dativ take an extra '-n' (den Kindern), except plurals already ending in -n or -s (den Autos)."
            )
        ),
        "topic_a2_02_two_way_preps" to TopicReference(
            rules = listOf(
                "The 9 two-way prepositions (Wechselpräpositionen): an, auf, hinter, in, neben, über, unter, vor, zwischen.",
                "Static location (Wo? / Where?) triggers DATIV: 'Das Bild hängt an der Wand'.",
                "Direction or movement across a boundary (Wohin? / Where to?) triggers AKKUSATIV: 'Ich hänge das Bild an die Wand'."
            ),
            tips = listOf(
                "Pair rule: liegen/legen, stehen/stellen, sitzen/setzen, hängen/hängen. The first of each pair is state (Dativ), the second is active placement (Akkusativ).",
                "Contractions: an + dem = am, in + dem = im, an + das = ans, in + das = ins."
            ),
            exceptions = listOf(
                "Abstract usages often fix one case: 'denken an' (+ Akk), 'warten auf' (+ Akk), 'teilnehmen an' (+ Dat)."
            )
        ),
        "topic_a2_07_subordinate_clauses" to TopicReference(
            rules = listOf(
                "Subordinating conjunctions (weil, dass, wenn, ob) kick the conjugated finite verb to the very end of the subordinate clause.",
                "Separable verbs in subordinate clauses do NOT split; the prefix stays attached to the conjugated verb at the end.",
                "If the sentence begins with the subordinate clause, the main clause starts immediately with its finite verb (Verb-First)."
            ),
            tips = listOf(
                "Memory trick: 'Verbs meet at the comma!' e.g.: 'Weil es regnet (Verb at end), bleibe (Verb at start) ich hier.'",
                "'ob' means 'whether / if' for indirect questions with yes/no answers: 'Ich weiß nicht, ob er kommt'."
            ),
            exceptions = listOf(
                "Do not confuse 'wenn' (if / whenever) with 'wann' (at what time? question word) or 'als' (when in past single event)."
            )
        ),

        // ========================== B1 TOPICS ==========================
        "topic_01_cases" to TopicReference(
            rules = listOf(
                "Genitiv indicates possession, belonging, and relations ('Wessen? / Whose?').",
                "Masculine and neuter singular nouns add -(e)s: des Vaters, des Hauses, des Kindes. Feminine and plural take 'der' and add NO ending.",
                "N-Deklination weak masculine nouns add -(e)n in Akkusativ, Dativ, and Genitiv singular: des Studenten, dem Kunden.",
                "Core Genitiv prepositions: wegen, während, trotz, anstatt / statt, innerhalb, außerhalb."
            ),
            tips = listOf(
                "Monosyllabic nouns (des Mannes, des Tisches) and nouns ending in -s, -ß, -z take '-es'. Multisyllabic nouns take '-s' (des Lehrers).",
                "In Goethe B1 written exams, always use Genitiv with 'wegen' and 'trotz' rather than spoken Dativ."
            ),
            exceptions = listOf(
                "'das Herz' is the only neuter noun with N-Deklination (des Herzens, dem Herzen).",
                "'der Herr' takes '-n' in singular ('den Herrn'), but '-en' in plural ('die Herren')."
            )
        ),
        "topic_05_passive" to TopicReference(
            rules = listOf(
                "Passiv Präsens: werden (conjugated) + Partizip II at the end: 'Das Auto wird repariert'.",
                "Passiv Präteritum: wurde / wurden + Partizip II at the end: 'Das Auto wurde repariert'.",
                "Passiv Perfekt: ist + Partizip II + worden (never geworden!): 'Das Auto ist repariert worden'.",
                "Passiv with Modal Verbs: Modalverb (conjugated) + Partizip II + werden: 'Das Auto muss repariert werden'."
            ),
            tips = listOf(
                "Introduce human agents with 'von' + Dativ ('vom Arzt'). Introduce means, cause, or instruments with 'durch' + Akkusativ ('durch ein Erdbeben').",
                "If there is no direct object in active voice, form impersonal passive with dummy 'Es' or start with another element: 'Hier wird gearbeitet'."
            ),
            exceptions = listOf(
                "Reflexive verbs, verbs with 'sein' (gehen, fallen, sein), and verbs expressing possession (haben, besitzen) CANNOT form passive voice."
            )
        ),
        "topic_07_sentence_structure" to TopicReference(
            rules = listOf(
                "Sentence adverbs on Position 1 (deshalb, deswegen, trotzdem, sonst, außerdem) cause immediate subject-verb inversion (Verb in Position 2).",
                "Two-part connectors (Doppelkonnektoren): nicht nur ... sondern auch, sowohl ... als auch, weder ... noch, entweder ... oder, zwar ... aber.",
                "In 'je ... desto/umso ...': 'je' introduces a subordinate clause (verb-final), while 'desto' introduces a main clause with comparative in Position 1 (verb in Position 2)."
            ),
            tips = listOf(
                "Compare: 'weil' (conjunction -> verb-end), 'denn' (ADUSO -> position 0), 'deshalb' (adverb -> position 1, verb next).",
                "'weder ... noch' has negative meaning built-in: never add 'nicht' to it!"
            ),
            exceptions = listOf(
                "In 'je schneller du fährst, desto gefährlicher ist es': 'desto gefährlicher' together forms Position 1, so the verb 'ist' is Position 2."
            )
        ),
        "topic_08_infinitive_clauses" to TopicReference(
            rules = listOf(
                "Purpose clauses with the SAME subject use 'um ... zu + Infinitiv': 'Ich lerne Deutsch, um in Deutschland zu arbeiten'.",
                "Purpose clauses with DIFFERENT subjects MUST use 'damit' with verb-final word order: 'Ich helfe ihm, damit er die Prüfung besteht'.",
                "'ohne ... zu + Infinitiv' means 'without doing something': 'Er ging, ohne sich zu verabschieden'.",
                "'(an)statt ... zu + Infinitiv' means 'instead of doing something': 'Er spielte, anstatt zu lernen'."
            ),
            tips = listOf(
                "With separable verbs, 'zu' inserts between prefix and stem: 'aufzustehen', 'einzukaufen', 'abzureisen'.",
                "'damit' can always be used grammatically even for identical subjects, but 'um ... zu' is considered more elegant in B1 writing."
            ),
            exceptions = listOf(
                "Modal verbs (können, müssen, etc.), perception verbs (sehen, hören), and motion verbs (gehen, fahren) NEVER take 'zu' with an infinitive."
            )
        ),
        "topic_09_relative_clauses" to TopicReference(
            rules = listOf(
                "Relative pronouns match the antecedent in GENDER and NUMBER, but their CASE is determined by their function in the relative clause.",
                "Relative clauses are subordinate clauses: the conjugated finite verb is placed at the very end.",
                "Genitiv relative pronouns: 'dessen' for masculine and neuter, 'deren' for feminine and plural.",
                "If the relative pronoun is governed by a preposition, the preposition comes FIRST: 'der Mann, mit dem ich gesprochen habe'."
            ),
            tips = listOf(
                "Nouns directly following 'dessen' or 'deren' never take an article: 'dessen Auto', 'deren Kinder'.",
                "Dativ plural relative pronoun is 'denen' (NOT den!): 'die Kollegen, mit denen ich arbeite'."
            ),
            exceptions = listOf(
                "Prepositional relative clauses always take the case governed by that preposition, ignoring the antecedent's case in the main clause."
            )
        )
    )

    fun getRulesForTopic(topicId: String, summary: String = ""): List<String> {
        return referenceMap[topicId]?.rules ?: listOf(
            "Understand the core morphological structure and position of words in the sentence.",
            "Verify case (Nominativ, Akkusativ, Dativ, Genitiv) and gender agreement.",
            summary.ifBlank { "Master this essential German grammar pattern for Goethe-Zertifikat certification." }
        )
    }

    fun getTipsForTopic(topicId: String): List<String> {
        return referenceMap[topicId]?.tips ?: listOf(
            "Pay close attention to word order rules (Verb in Position 2 vs. Verb-final).",
            "Practice creating your own example sentences using this pattern daily."
        )
    }

    fun getExceptionsForTopic(topicId: String): List<String> {
        return referenceMap[topicId]?.exceptions ?: listOf(
            "Watch out for irregular forms and stem vowel changes.",
            "Colloquial spoken German may vary from strict Goethe exam standards."
        )
    }
}
