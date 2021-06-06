import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersFromRussian
{
    protected static RegexRule[] rules = {
            new RegexRule("(^ноль$)|((^нол)[юеях]$)", "0", -1),
            new RegexRule("(^один$)|((^одн)[огим]$)", "1", -1),
            new RegexRule("(^два$)|(^дву)[а-я]$", "2", -1),
            new RegexRule("(^три$)|(^тр)[ёех]$", "3", -1),
            new RegexRule("(^четыре$)|(^четыр)[ёх]$", "4", -1),
            new RegexRule("(^пять$)|(^пят)[и]$", "5", -1),
            new RegexRule("(^шесть$)|(^шест)[и]$", "6", -1),
            new RegexRule("(^семь$)|(^сем)[и]$", "7", -1),
            new RegexRule("(^восемь$)|(^восьм)[и]$", "8", -1),
            new RegexRule("(^девять$)|(^девят)[и]$", "9", -1),
            new RegexRule("(^десять$)|(^десят)[и]$", "10", -1),
            new RegexRule("(^одиннадцать$)|(^одиннадцат)[и]$", "11", -1),
            new RegexRule("(^двенадцать$)|(^двенадцат)[и]$", "12", -1),
            new RegexRule("(^тринадцать$)|(^тринадцат)[и]$", "13", -1),
            new RegexRule("(^четырнадцать$)|(^четырнадцат)[и]$", "14", -1),
            new RegexRule("(^пятнадцать$)|(^пятнадцат)[и]$", "15", -1),
            new RegexRule("(^шестнадцать$)|(^шестнадцат)[и]$", "16", -1),
            new RegexRule("(^семнадцать$)|(^семнадцат)[и]$", "17", -1),
            new RegexRule("(^восемнадцать$)|(^восемнадцати)[и]$", "18", -1),
            new RegexRule("(^девятнадцать$)|(^девятнадцат)[и]$", "19", -1),
            new RegexRule("(^двадцать$)|(^двадцат)[и]$", "20", 9),
            new RegexRule("(^тридцать$)|(^тридцат)[и]$", "30", 9),
            new RegexRule("(^сорок$)|(^сорок)[а]$", "40", 9),
            new RegexRule("(^пятдесят$)|(^пятидесят)[и]$", "50", 9),
            new RegexRule("(^шестьдесят$)|(^шестидесят)[и]$", "60", 9),
            new RegexRule("(^семьдесят$)|(^семидесят)[и]$", "70", 9),
            new RegexRule("(^восемьдесят$)|(^восьмидесят)[и]$", "80", 9),
            new RegexRule("(^девяносто$)|(^девяност)[а]$", "90", 9),
            new RegexRule("(^сто$)|(^ста$)", "100", 27),
    };

    protected String text; // я добавил комментарий снова

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
            Matcher matcher = pattern.matcher(text.toLowerCase(Locale.ROOT));
            if (!matcher.matches()) throw new NoMatch();
            return matcher.replaceAll(replace_to);
        }

        protected static class NoMatch extends Exception {}
    }
}
