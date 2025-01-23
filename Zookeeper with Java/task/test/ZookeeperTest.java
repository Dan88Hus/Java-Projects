import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Map;

public class ZookeeperTest extends StageTest {

    final String camel = """
        Switching on the camera in the camel habitat...
         ___.-''''-.
        /___  @    |
        ',,,,.     |         _.'''''''._
             '     |        /           \\
             |     \\    _.-'             \\
             |      '.-'                  '-.
             |                               ',
             |                                '',
              ',,-,                           ':;
                   ',,| ;,,                 ,' ;;
                      ! ; !'',,,',',,,,'!  ;   ;:
                     : ;  ! !       ! ! ;  ;   :;
                     ; ;   ! !      ! !  ; ;   ;,
                    ; ;    ! !     ! !   ; ;
                    ; ;    ! !    ! !     ; ;
                   ;,,      !,!   !,!     ;,;
                   /_I      L_I   L_I     /_I
        Look at that! Our little camel is sunbathing!""";

    final String lion = """
        Switching on the camera in the lion habitat...
                                                       ,w.
                                                     ,YWMMw  ,M  ,
                                _.---.._   __..---._.'MMMMMw,wMWmW,
                           _.-""        '''           YP"WMMMMMMMMMb,
                        .-' __.'                   .'     MMMMW^WMMMM;
            _,        .'.-'"; `,       /`     .--""      :MMM[==MWMW^;
         ,mM^"     ,-'.'   /   ;      ;      /   ,       MMMMb_wMW"  @\\
        ,MM:.    .'.-'   .'     ;     `\\    ;     `,     MMMMMMMW `"=./`-,
        WMMm__,-'.'     /      _.\\      F'''-+,,   ;_,_.dMMMMMMMM[,_ / `=_}
        "^MP__.-'    ,-' _.--""   `-,   ;       \\  ; ;MMMMMMMMMMW^``; __|
                   /   .'            ; ;         )  )`{  \\ `"^W^`,   \\  :
                  /  .'             /  (       .'  /     Ww._     `.  `"
                 /  Y,              `,  `-,=,_{   ;      MMMP`""-,  `-._.-,
                (--, )                `,_ / `) \\/"")      ^"      `-, -;"\\:
        The lion is roaring!""";

    final String deer = """
        Switching on the camera in the deer habitat...
           /|       |\\
        `__\\       //__'
           ||      ||
         \\__`\\     |'__/
           `_\\   //_'
           _.,:---;,._
           \\_:     :_/
             |@. .@|
             |     |
             ,\\.-./ \\
             ;;`-'   `---__________-----.-.
             ;;;                         \\_\\
             ';;;                         |
              ;    |                      ;
               \\   \\     \\        |      /
                \\_, \\    /        \\     |\\
                  |';|  |,,,,,,,,/ \\    \\ \\_
                  |  |  |           \\   /   |
                  \\  \\  |           |  / \\  |
                   | || |           | |   | |
                   | || |           | |   | |
                   | || |           | |   | |
                   |_||_|           |_|   |_|
                  /_//_/           /_/   /_/
        Our 'Bambi' looks hungry. Let's go to feed it!""";

    final String goose = """
        Switching on the camera in the goose habitat...
        
                                            _
                                        ,-"" "".
                                      ,'  ____  `.
                                    ,'  ,'    `.  `._
           (`.         _..--.._   ,'  ,'        \\    \\
          (`-.\\    .-""        ""'   /          (  d _b
         (`._  `-"" ,._             (            `-(   \\
         <_  `     (  <`<            \\              `-._\\
          <`-       (__< <           :
           (__        (_<_<          ;
            `------------------------------------------
        The goose is staring intently at you... Maybe it's time to change the channel?""";

    final String bat = """
        Switching on the camera in the bat habitat...
        _________________               _________________
         ~-.              \\  |\\___/|  /              .-~
             ~-.           \\ / o o \\ /           .-~
                >           \\  W  //           <
               /             /~---~\\             \\
              /_            |       |            _\\
                 ~-.        |       |        .-~
                    ;        \\     /        i
                   /___      /\\   /\\      ___\\
                        ~-. /  \\_/  \\ .-~
                           V         V
        This bat looks like it's doing fine.""";

    final String rabbit = """
        Switching on the camera in the rabbit habitat...
                 ,
                /|      __
               / |   ,-~ /
              Y :|  //  /
              | jj /( .^
              >-"~"-v"
             /       Y
            jo  o    |
           ( ~T~     j
            >._-' _./
           /   "~"  |
          Y     _,  |
         /| ;-"~ _  l
        / l/ ,-"~    \\
        \\//\\/      .- \\
         Y        /    Y
         l       I     !
         ]\\      _\\    /"\\
        (" ~----( ~   Y.  )
        It looks like we will soon have more rabbits!""";

    private final String theEndMessage = "---\n" +
        "You've reached the end of the program. " +
        "To check another habitat, please restart the watcher.";

    private final String[] indexArray = {"0", "1", "2", "3", "4", "5"};

    private final Map<String, String[]> animalIndex = Map.of(
        "0", new String[]{camel, "camel"},
        "1", new String[]{lion, "lion"},
        "2", new String[]{deer, "deer"},
        "3", new String[]{goose, "goose"},
        "4", new String[]{bat, "bat"},
        "5", new String[]{rabbit, "rabbit"}
    );

    @DynamicTest(data = "indexArray")
    CheckResult test(String index) {
        var testedProgram = new TestedProgram();
        testedProgram.start();

        var output = testedProgram.execute(index);

        var animalImage = animalIndex.get(index)[0];
        var animalName = animalIndex.get(index)[1];

        if (!output.contains(animalImage)) {
            return CheckResult.wrong("You should output a " + animalName + " " +
                "when the input is the number " + index);
        }

        if (!output.contains(theEndMessage)) {
            return CheckResult.wrong("You should output the message about the end of the program!");
        }

        return CheckResult.correct();
    }
}