package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static int num;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> list = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));

                line = br.readLine();
            }

            list.sort(Comparator.comparingDouble(Sale::averagePrice).reversed());

            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");

            List<Sale> sales = list.stream()
                    .filter(p -> p.getYear() == 2016)
                    .limit(5)
                    .collect(Collectors.toList());

            sales.forEach(System.out::println);
            System.out.println();

            double sum = list.stream().filter(p -> p.getSeller().equals("Logan"))
                    .filter(p -> p.getMonth() <= 7)
                    .filter(p -> p.getYear() == 2016)
                    .mapToDouble(Sale::getTotal).sum();

            System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f",sum));

        } catch (IOException e) {
            System.out.println("Erro: " + path + " " + e.getMessage());
        }

        sc.close();
    }
}