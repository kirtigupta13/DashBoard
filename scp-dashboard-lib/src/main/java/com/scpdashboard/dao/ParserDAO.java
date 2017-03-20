package com.scpdashboard.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.scpdashboard.exceptions.NotValidServerFileException;

/**
 * An Interface for Parser
 * 
 * @author SD049814
 *
 * @param <T>
 */

public interface ParserDAO<T> {
    List<T> parseFile(BufferedReader reader) throws IOException, NumberFormatException, NotValidServerFileException;
}
