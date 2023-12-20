package org.accolite;

import org.accolite.entity.PlayerStatistics;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        Set<PlayerStatistics> statisticsSet = getExcelData("C:\\Users\\bharath.m\\IdeaProjects\\EvaluationDayProblem\\src\\main\\resources\\Statistics.xlsx");

        PlayerStatistics highestScorer =       statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getScores).reversed())
                        .collect(Collectors.toList()).stream().findFirst().get();
        System.out.println(highestScorer.getPlayerName()+" "+ highestScorer.getScores());
        System.out.println();
        PlayerStatistics secondHighestScorer =
                statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getScores).reversed()).collect(Collectors.toList()).stream().findFirst().get();
        System.out.println(secondHighestScorer.getPlayerName()+" "+secondHighestScorer.getScores());
        System.out.println();
        PlayerStatistics leastScorer = statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getScores)).findFirst().get();
        System.out.println(leastScorer.getPlayerName()+" "+leastScorer.getHighestScore());
        System.out.println();
        PlayerStatistics oldestPlayer = statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getBirthDate))
                .collect(Collectors.toList()).get(0);
        System.out.println(oldestPlayer.getPlayerName()+" "+ oldestPlayer.getBirthDate());
        Map<Integer, List<PlayerStatistics>> groupPlayers = statisticsSet.stream().collect(Collectors.groupingBy(playerStatistics -> playerStatistics.getBirthDate().getYear() / 10, Collectors.filtering(p -> p.getBirthDate().getYear() / 10 >=0, Collectors.toList())));
        groupPlayers.forEach(
                (x,y)->{
                    System.out.println(x*10+"'s");
                    y.stream().forEach(
                            xy-> System.out.println(xy.getPlayerName()+" "+xy.getBirthDate())
                    );
                }
        );
        System.out.println();
        System.out.println("Top Scores On Decades");
        groupPlayers.forEach(
                (x,y)->{
                    System.out.println(x*10+"'s");
                    System.out.println(y.stream().sorted(Comparator.comparing(PlayerStatistics::getScores).reversed()).collect(Collectors.toList()).stream().findFirst().get());
                }
        );
        System.out.println();
        System.out.println("Highest Strike Rate");
        System.out.println(statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getStrikeRate).reversed()).collect(Collectors.toList()).stream().findFirst());
        System.out.println();

        statisticsSet.stream().forEach(
                x-> System.out.println(x.getPlayerName()+" "+x.getWicketTaken()+" "+x.getMatchesPlayed()+" "+((float)x.getWicketTaken()/x.getMatchesPlayed()))
        );
        System.out.println();
        System.out.println("Highest Average Wicket Taker ");

        System.out.println(statisticsSet.stream().sorted(Comparator.comparing(PlayerStatistics::getAverage).reversed()).collect(Collectors.toList()).stream().findFirst().get());
        System.out.println();

        System.out.println("Indian Batsman");
        statisticsSet.stream().filter(playerStatistics -> playerStatistics.getCountryName().equals("INDIA")).forEach(
                x-> System.out.print(x.getPlayerName()+"\t")
        );
        System.out.println();


    }
    public static Set<PlayerStatistics> getExcelData(String fileName){
        Set<PlayerStatistics> statisticsSet = new LinkedHashSet<>();
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                PlayerStatistics statistics = new PlayerStatistics();
                statistics.setPlayerName(row.getCell(0).getStringCellValue().toUpperCase());
                statistics.setCountryName(row.getCell(1).getStringCellValue().toUpperCase());
                statistics.setBirthDate(row.getCell(2).getDateCellValue());
                statistics.setScores((long) row.getCell(3).getNumericCellValue());
                statistics.setMatchesPlayed((int) row.getCell(4).getNumericCellValue());
                statistics.setWicketTaken((int) row.getCell(5).getNumericCellValue());
                statistics.setHighestScore((int) row.getCell(6).getNumericCellValue());
                statistics.setAverage( row.getCell(7).getNumericCellValue());
                statistics.setStrikeRate(row.getCell(8).getNumericCellValue());
                statisticsSet.add(statistics);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return statisticsSet;
    }
}