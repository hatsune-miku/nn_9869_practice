/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package test.parser;

import nn.ExternalInputTable;

import java.util.Arrays;

public class ExternalInputParser extends AbstractFileParser<ExternalInputTable> {
    public ExternalInputParser(String filename) throws FileUnreadableException {
        super(filename);
    }

    @Override
    public ExternalInputTable parse() {
        return new ExternalInputTable(
            Arrays.stream(
                getScanner()
                    .nextLine()
                    .strip()
                    .split(", ")
            )
                .mapToDouble(Double::parseDouble)
                .toArray()
        );
    }
}
