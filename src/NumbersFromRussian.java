import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersFromRussian
{
    protected static RegexRule[] rules = {
            new RegexRule("[a]", "0", -1),
            new RegexRule("[b]", "1", -1),
            new RegexRule("[c]", "2", -1),
            new RegexRule("[d]", "3", -1),
            /*new RegexRule("", "4"),
            new RegexRule("", "5"),
            new RegexRule("", "6"),
            new RegexRule("", "7"),
            new RegexRule("", "8"),
            new RegexRule("", "9"),
            new RegexRule("", "10"),
            new RegexRule("", "11"),
            new RegexRule("", "12"),
            new RegexRule("", "13"),
            new RegexRule("", "14"),
            new RegexRule("", "15"),
            new RegexRule("", "16"),
            new RegexRule("", "17"),
            new RegexRule("", "18"),
            new RegexRule("", "19"),
            new RegexRule("", "20"),
            new RegexRule("", "30"),
            new RegexRule("", "40"),
            new RegexRule("", "50"),
            new RegexRule("", "60"),
            new RegexRule("", "70"),
            new RegexRule("", "80"),
            new RegexRule("", "90"),
            new RegexRule("", "100"),*/
    };

    protected String text;

    protected NumbersFromRussian(String from)
    {
        int res = -1; // по-умолчанию флаг отсутствия числа
        int check_index = rules.length - 1;

        StringBuilder text_processed = new StringBuilder();

        for (String word:
             from.split(" ")) //todo переделать на регекс со знаками препинания
        {
            boolean finded = false;
            while (check_index >= 0)
            {
                RegexRule rule = rules[check_index];
                try
                {
                    String parsed = rule.process(word);
                    if (res == -1) res = 0; //число найдено -- снимаем флаг
                    res += Integer.parseInt(parsed);
                    check_index = rule.success_next_index;
                    finded = true;
                    break;
                }
                catch (RegexRule.NoMatch noMatch)
                {
                    check_index--;
                }
            }

            if (check_index < 0)
            {
                if (res >= 0)
                {
                    text_processed.append(res).append(' ');
                }
                if (!finded)
                {
                    text_processed.append(word).append(' ');
                }
                res = -1;
                check_index = rules.length - 1;
            }
        }

        if (res >= 0)
        {
            text_processed.append(res);
        }

        text = text_processed.toString();
    }

    protected static Pattern check_pattern = Pattern.compile("");

    @Override
    public String toString()
    {
        return this.text;
    }

    public static NumbersFromRussian process(String from)
    {
        return new NumbersFromRussian(from);
    }

    protected static class RegexRule
    {
        protected Pattern pattern;
        protected String replace_to;
        public final int success_next_index;
        public RegexRule(String regex, String replace_to, int success_next_index)
        {
            pattern = Pattern.compile(regex);
            this.replace_to = replace_to;
            this.success_next_index = success_next_index;
        }

        public String process(String text) throws NoMatch
        {
            Matcher matcher = pattern.matcher(text);
            if (!matcher.matches()) throw new NoMatch();
            return matcher.replaceAll(replace_to);
        }

        protected static class NoMatch extends Exception {}
    }
}
