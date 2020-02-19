/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.scheduling;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author mbakr 
 * 
 */
public class AG_Scheduling {
    public static Scanner input = new Scanner(System.in);
    public static Vector<Process> myProcesses = new Vector<>();
    public static Vector<Process> readyQueue = new Vector<>();
    public static Vector<Process> processes = new Vector<>();
    public static Vector<String> excutionOrder = new Vector<>();
    public static int quantum;
    public static int Actual_time = 0;
    public static int ready_Process = 0;
    public static int counter = 0;
    public static Process running = new Process();
    

    public static void get_processes() 
    {
        int num;
        System.out.print("Enter number of processes : ");
        num = input.nextInt();
        System.out.print("Enter quantum of processes : ");
        quantum = input.nextInt();
        System.out.println();

        for (int i = 0; i < num; i++) 
        {

            Process process = new Process();
            Process p = new Process();
            System.out.print("Enter Process No." + (i + 1) + " Name : ");
            String name = input.next();
            System.out.print("Enter Process No." + (i + 1) + " arrival time : ");
            int arrvial = input.nextInt();
            System.out.print("Enter Process No." + (i + 1) + " burst time : ");
            int burst = input.nextInt();
            System.out.print("Enter Process No." + (i + 1) + " Priority : ");
            int priority = input.nextInt();
            
            process.process_name = name;
            process.arrival_time = arrvial;
            process.burst_time = burst;
            process.priority = priority;
            process.waiting_time = 0;
            
            p.process_name = name;
            p.arrival_time = arrvial;
            p.burst_time = burst;
            p.priority = priority;
            p.waiting_time = 0;
            p.appearance = false;
            
            process.AG_factor = process.arrival_time + process.burst_time + process.priority;
            myProcesses.add(process);
            processes.add(p);
            myProcesses.get(i).quantum = quantum;
            process.historyQuantum.add(quantum);
            p.historyQuantum.add(quantum);
            System.out.println();
        }

        bubbleSort_vector();

    }

    public static void bubbleSort_vector() 
    {
        for (int i = 0; i < myProcesses.size() - 1; i++) 
        {
            for (int j = 0; j < myProcesses.size() - i - 1; j++) 
            {
                if (myProcesses.get(j).arrival_time > myProcesses.get(j + 1).arrival_time) 
                {
                    int arrival = myProcesses.get(j).arrival_time;
                    myProcesses.get(j).arrival_time = myProcesses.get(j + 1).arrival_time;
                    myProcesses.get(j + 1).arrival_time = arrival;

                    int burst = myProcesses.get(j).burst_time;
                    myProcesses.get(j).burst_time = myProcesses.get(j + 1).burst_time;
                    myProcesses.get(j + 1).burst_time = burst;

                    int priority = myProcesses.get(j).priority;
                    myProcesses.get(j).priority = myProcesses.get(j + 1).priority;
                    myProcesses.get(j + 1).priority = priority;

                    int AG = myProcesses.get(j).AG_factor;
                    myProcesses.get(j).AG_factor = myProcesses.get(j + 1).AG_factor;
                    myProcesses.get(j + 1).AG_factor = AG;

                    String name = myProcesses.get(j).process_name;
                    myProcesses.get(j).process_name = myProcesses.get(j + 1).process_name;
                    myProcesses.get(j + 1).process_name = name;
                
                    name = processes.get(j).process_name;
                    processes.get(j).process_name = processes.get(j + 1).process_name;
                    processes.get(j + 1).process_name = name;
                    
                    arrival = processes.get(j).arrival_time;
                    processes.get(j).arrival_time = processes.get(j + 1).arrival_time;
                    processes.get(j + 1).arrival_time = arrival;

                    burst = processes.get(j).burst_time;
                    processes.get(j).burst_time = processes.get(j + 1).burst_time;
                    processes.get(j + 1).burst_time = burst;

                    priority = processes.get(j).priority;
                    processes.get(j).priority = processes.get(j + 1).priority;
                    processes.get(j + 1).priority = priority;

                    AG = processes.get(j).AG_factor;
                    processes.get(j).AG_factor = processes.get(j + 1).AG_factor;
                    processes.get(j + 1).AG_factor = AG;

                    name = processes.get(j).process_name;
                    processes.get(j).process_name = processes.get(j + 1).process_name;
                    processes.get(j + 1).process_name = name;
                }
            }
        }
    }

    public static void check_time() 
    {
        if (!myProcesses.isEmpty()) 
        {
            for (int i = 0; i < myProcesses.size(); i++) 
            {
                if (myProcesses.get(i).arrival_time <= Actual_time) 
                {
                    readyQueue.add(myProcesses.get(i));
                    myProcesses.remove(i);
                }

            }
        }
    }

    public static Process smallest_AG()
    {
        if (!readyQueue.isEmpty()) 
        {
            Process smallest = readyQueue.get(0);
            ready_Process = 0;
            for (int i = 0; i < readyQueue.size(); i++) 
            {
                if (readyQueue.get(i).AG_factor < smallest.AG_factor) 
                {
                    smallest = readyQueue.get(i);

                    ready_Process = i;  // index of suitable process in readyQeue
                }
            }

            return smallest;
        }
        return null;
    }

    public static boolean replace_process() 
    {
        boolean bool = false;

        Process smallest = smallest_AG();  //get the smallest ag in ready Queue

        if (smallest != null && smallest.AG_factor < running.AG_factor) // check replace condition
        {
            // now we do change
            bool = true;

            running.burst_time -= (Actual_time - running.start_time); //decrease burst time
            running.burst_time += counter;
            counter = 0;
            
            running.quantum += (int) Math.ceil(running.quantum - (Actual_time - running.start_time));
            Process p = search(running);
            p.historyQuantum.add(running.quantum);
            if (running.burst_time > 0) // finished its burst time
            {
                readyQueue.add(running);
            }
            running.end_time = Actual_time;
            running = smallest;
            running.start_time = Actual_time;
            running.exe_time = 0;
            p = search(running);
            if(p.waiting_time==0 && p.appearance == false)
            {
                p.waiting_time += Math.abs(Actual_time - running.arrival_time);
                running.appearance = true;
                p.appearance = true;
            }
            else
                p.waiting_time += Actual_time - running.end_time ;            
            readyQueue.remove(ready_Process);
        }
        return bool;
    }

    public static int mean_quantum() 
    {
        double mean;
        double num = 0;
        double sum = 0;

        for (int i = 0; i < readyQueue.size(); i++) 
        {
            num++;
            sum += readyQueue.get(i).quantum;
        }

        num++;                  // to add the running process
        sum += running.quantum;   // to add the running process

        mean = sum / num;

        return (int) Math.ceil(mean / 10);

    }

    public static void print()
    {
        for(int i = 0; i < excutionOrder.size(); i++)
        {
            System.err.print(excutionOrder.get(i) + " >>> ");
        }
        System.err.println();
        double average_wait = 0, average_turn = 0;
        for(int i = 0 ; i < processes.size(); i++)
        {
            average_turn += processes.get(i).turnaround_time;
            average_wait += processes.get(i).waiting_time;
            System.out.println("Process " + processes.get(i).process_name + " , Waiting Time : " + processes.get(i).waiting_time + " , Turnarounf Time : " + processes.get(i).turnaround_time);
        }
        average_turn /= processes.size();
        average_wait /= processes.size();
        System.out.println("Average Waiting Time  = " + average_wait);
        System.out.println("Average Turnaround Time  = " + average_turn);
        System.out.println("History Of Quantum : ");
        for(int i = 0; i < processes.size(); i++)
        {
            System.out.print("Process " + processes.get(i).process_name + " :  ");
            for(int j = 0; j <processes.get(i).historyQuantum.size(); j++)
            {
                System.out.print(processes.get(i).historyQuantum.get(j) + " >>> ");
            }
            System.out.println();
        }
    }

    public static void set_turnaround()
    {
        for(int i = 0; i < processes.size(); i++)
        {
            processes.get(i).turnaround_time = processes.get(i).waiting_time + processes.get(i).burst_time;
        }
    }

    public static Process search(Process P)
    {
        for(int i = 0; i < processes.size(); i++)
        {
            if(processes.get(i).process_name.equals(P.process_name))
                return processes.get(i);
        }
        return null;
    }
    
    public static void AG_scheduler() 
    {
        Actual_time = myProcesses.get(0).arrival_time;
        check_time(); // add suitable process in readyQueue
        running = readyQueue.get(0);
        readyQueue.remove(0);
        running.start_time = Actual_time;
        running.exe_time = 0;
        Actual_time = running.arrival_time;
        running.appearance = true;
        processes.get(0).appearance = true;
        while (!readyQueue.isEmpty() || running != null) 
        {
            excutionOrder.add(running.process_name);
            if ((running.burst_time - (int) Math.ceil(running.quantum / 2.0)) <= 0) 
            {
                while (running.burst_time > 0) 
                {
                    Actual_time++;
                    running.exe_time++;
                    check_time();

                    running.burst_time -= running.exe_time;
                }
                if (!readyQueue.isEmpty()) 
                {
                    running.end_time = Actual_time;
                    running = readyQueue.get(0);
                    readyQueue.remove(0);
                    running.start_time = Actual_time;
                    running.exe_time = 0;
                    Process p = search(running);
                    if(p.waiting_time==0 && p.appearance == false)
                    {
                        p.waiting_time += Actual_time - running.arrival_time;
                        p.appearance = true;
                        running.appearance = true;
                    }
                    else
                        p.waiting_time += Actual_time - running.end_time;

                    
                } 
                else 
                {
                    running = null;
                }
                continue;
            }

            Actual_time += (int) Math.ceil(running.quantum / 2.0);
            running.exe_time += (int) Math.ceil(running.quantum / 2.0);

            check_time();

            boolean check = replace_process();
            if (check) 
            {
                continue;
            }

            if (check == false) 
            {
                boolean bool = false;

                counter = 0;

                while (running.exe_time < running.quantum) 
                {
                    if (counter == 0)
                    {
                        running.burst_time -= (int) Math.ceil(running.quantum / 2.0);
                        counter += (int) Math.ceil(running.quantum / 2.0);
                    }
                    Actual_time++;
                    running.exe_time++;
                    running.burst_time--;
                    counter++;
                    if (running.burst_time == 0) 
                    {
                        bool = true;
                        if (!readyQueue.isEmpty()) 
                        {
                            running.end_time = Actual_time;
                            running = readyQueue.get(0);
                            readyQueue.remove(0);
                            running.start_time = Actual_time;
                            running.exe_time = 0;
                            Process p = search(running);
                            if(p.waiting_time==0 && p.appearance == false)
                            {
                                p.waiting_time += Actual_time - running.arrival_time;
                                p.appearance = true;
                                running.appearance = true;
                            }
                            else 
                                p.waiting_time += Actual_time - running.end_time;                            
                            
                        }
                        else 
                            running = null;
                        break;
                    }
                    check_time();
                    
                    check = replace_process();
                    if (check) 
                    {
                        bool = true;
                        break;
                    }
                }

                if (bool) 
                {
                    continue;
                }

                running.burst_time = running.burst_time - running.exe_time + counter;
                counter = 0;

                if (running.burst_time != 0) 
                {
                    running.quantum += mean_quantum();
                    Process p = search(running);
                    p.historyQuantum.add(running.quantum);
                }

                if (running.burst_time > 0)
                {
                    readyQueue.add(running);
                }
                if (readyQueue.isEmpty()) 
                {
                    running = null;
                } 
                else 
                {
                    running.end_time = Actual_time;
                    running = readyQueue.get(0);
                    readyQueue.remove(0);
                    running.start_time = Actual_time;
                    running.exe_time = 0;
                    Process p = search(running);
                    if(p.waiting_time==0 && p.appearance == false)
                    {
                        p.waiting_time += Actual_time - running.arrival_time;
                        p.appearance = true;
                        running.appearance = true;
                    }
                    else 
                        p.waiting_time += Actual_time - running.end_time;
                }
            }
        }
    }
}
