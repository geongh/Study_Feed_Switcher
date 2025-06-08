import androidx.compose.ui.graphics.Color
import com.solaisc.studyfeedswitcher.ui.theme.Blue1
import com.solaisc.studyfeedswitcher.ui.theme.Blue2
import com.solaisc.studyfeedswitcher.ui.theme.Brown1
import com.solaisc.studyfeedswitcher.ui.theme.Brown2
import com.solaisc.studyfeedswitcher.ui.theme.Coral1
import com.solaisc.studyfeedswitcher.ui.theme.Coral2
import com.solaisc.studyfeedswitcher.ui.theme.Grass1
import com.solaisc.studyfeedswitcher.ui.theme.Grass2
import com.solaisc.studyfeedswitcher.ui.theme.Jean1
import com.solaisc.studyfeedswitcher.ui.theme.Jean2
import com.solaisc.studyfeedswitcher.ui.theme.Orange1
import com.solaisc.studyfeedswitcher.ui.theme.Orange2
import com.solaisc.studyfeedswitcher.ui.theme.Plum1
import com.solaisc.studyfeedswitcher.ui.theme.Plum2
import com.solaisc.studyfeedswitcher.ui.theme.Teal1
import com.solaisc.studyfeedswitcher.ui.theme.Teal2

// Quick Lessons Reference:
// 1. “Inside a Cell” • A 60‑second tour of organelles • Biology • Leaf‑green gradient
// 2. “Fractions in a Flash” • Understand halves, thirds, and quarters fast • Math • Indigo gradient
// 3. “WWII at a Glance” • Key events and dates distilled into one minute • History • Amber gradient
// 4. “Bonjour Basics” • Greet and introduce yourself in French • Language • Denim‑blue gradient
// 5. “Sketch Like Picasso” • Quick warm‑up for abstract line art • Art • Coral gradient
// 6. “Binary in 60 Seconds” • Count to 32 on one hand using binary • Computer Science • Teal gradient
// 7. “Chord Progression 101” • The I‑V‑vi‑IV pattern explained • Music • Plum gradient
// 8. “Mountains & Valleys” • How tectonic plates shape landscapes • Geography • Earth‑brown gradient

data class QuickLesson(
    val title: String,
    val description: String,
    val subject: String,
    val gradient: Gradient
)

data class Gradient(
    val name: String,
    val color1: Color,
    val color2: Color
)

val quickLessons = listOf(
    QuickLesson("Inside a Cell", "A 60‑second tour of organelles", "Biology", Gradient("Leaf‑green gradient", Grass1, Grass2)),
    QuickLesson("Fractions in a Flash", "Understand halves, thirds, and quarters fast", "Math", Gradient("Indigo gradient", Blue1, Blue2)),
    QuickLesson("WWII at a Glance", "Key events and dates distilled into one minute", "History", Gradient("Amber gradient", Orange1,  Orange2)),
    QuickLesson("Bonjour Basics", "Greet and introduce yourself in French", "Language", Gradient("Denim‑blue gradient", Jean1, Jean2)),
    QuickLesson("Sketch Like Picasso", "Quick warm‑up for abstract line art", "Art", Gradient("Coral gradient", Coral1, Coral2)),
    QuickLesson("Binary in 60 Seconds", "Count to 32 on one hand using binary", "Computer Science", Gradient("Teal gradient", Teal1, Teal2)),
    QuickLesson("Chord Progression 101", "The I‑V‑vi‑IV pattern explained", "Music", Gradient("Plum gradient", Plum1, Plum2)),
    QuickLesson("Mountains & Valleys", "How tectonic plates shape landscapes", "Geography", Gradient("Earth‑brown gradient", Brown1, Brown2))
)