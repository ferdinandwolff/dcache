package org.dcache.util;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * A glob is a pattern used for limitted pattern matching. The class
 * supports single character (question mark) and multi character
 * (asterix) wildcards, similar to Unix shell globbing.
 *
 * Due to its simplicity, a glob is easy to translate to other pattern
 * matching language.
 *
 * The current implementation does not have an escape symbol.
 */
public class Glob implements Serializable
{
    static final long serialVersionUID = -5052804169005574207L;

    private final String _pattern;

    public Glob(String s)
    {
        _pattern = s;
    }

    public boolean matches(String s)
    {
        return toPattern().matcher(s).matches();
    }

    public Pattern toPattern()
    {
        StringBuffer s = new StringBuffer();
        int j = 0;
        for (int i = 0; i < _pattern.length(); i++) {
            switch (_pattern.charAt(i)) {
            case '?':
                s.append(Pattern.quote(_pattern.substring(j, i)));
                s.append(".");
                j = i + 1;
                break;

            case '*':
                s.append(Pattern.quote(_pattern.substring(j, i)));
                s.append(".*");
                j = i + 1;
                break;

            default:
                break;
            }
        }
        s.append(Pattern.quote(_pattern.substring(j)));
        return Pattern.compile(s.toString());
    }
}