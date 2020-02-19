  package cpu.scheduling;
     
    import java.util.ArrayList;
    import java.util.Scanner;
     
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
     
    /**
     *
     * @author mbakr,nardine,mohsen,maria,sameh ya3am
     */
    public class SJF_Scheduling 
    {
        ArrayList<Process> processes = new ArrayList<Process>();
        ArrayList<Process> done = new ArrayList<Process>();
        ArrayList<Process> processes2 = new ArrayList<Process>();
        ArrayList<Process> processes3 = new ArrayList<Process>();
        ArrayList<Process> arrived2 = new ArrayList<Process>();
     
        public void getInput() throws CloneNotSupportedException
        {
            System.out.print("Enter number of processes : ");
            int NumberOfProcess;
            Scanner in = new Scanner(System.in);
            NumberOfProcess = in.nextInt();
            System.out.println();
            for (int i = 0; i < NumberOfProcess; i++)
            {
                Process p = new Process();
                System.out.print("Enter Process No." + (i + 1) + " Name : ");
                p.process_name = in.next();
                System.out.print("Enter Process No." + (i + 1) + " Arrival Time : ");
                p.arrival_time = in.nextInt();
                System.out.print("Enter Process No." + (i + 1) + " Busrt Time : ");
                p.burst_time = in.nextInt();
                processes.add(p);
                Process d = (Process) p.clone();
                processes2.add(d);
                Process p2 = (Process) p.clone();
                processes3.add(p2);
                System.out.println();
            }
        }
     
        void IncreamentTime(int value) 
        {
            for (int j = 0; j < value; j++)
            {
                for (int i = 0; i < processes.size(); i++)
                {
                    if (processes.get(i).arrival_time == j && !arrived2.contains(processes.get(i))) 
                    {
                        arrived2.add(processes.get(i));
                    }
                }
            }
        }
     
        private int getMinArrivalTime()
        {
            int min = processes.get(0).arrival_time;
            for (int i = 1; i < processes.size(); i++)
            {
                if (processes.get(i).arrival_time < min)
                {
                    min = processes.get(i).arrival_time;
                }
            }
            return min;
        }
     
        private Process findMin(ArrayList<Process> processes4)//returns the miinimum burst time
        {
            int mi = processes4.get(0).burst_time;
            int j = 0;
            for (int i = 1; i < processes4.size(); i++)
            {
                if (processes4.get(i).burst_time < mi)
                {
                    mi = processes4.get(i).burst_time;
                    j = i;
                } 
                else if (processes4.get(i).burst_time == mi) 
                {
                    if (processes4.get(i).arrival_time < processes4.get(j).arrival_time)
                    {
                        j = i;
                    }
                }
            }
            return processes4.get(j);
        }
     
        private int getArrivalTime(Process p) 
        {
            int temp = 0;
            for (int i = 0; i < processes3.size(); i++) 
            {
                if (p.process_name.equals(processes3.get(i).process_name)) 
                {
                    temp = processes3.get(i).arrival_time;
                    break;
                }
            }
            return temp;
        }
     
        private int getBurstTime(Process p) 
        {
            int x = 0;
            for (int i = 0; i < processes3.size(); i++) 
            {
                if (p.process_name.equals(processes3.get(i).process_name)) 
                {
                    x = processes3.get(i).burst_time;
                    break;
                }
            }
            return x;
        }
     
        public void getData() 
        {
            for (int i = 0; i < done.size(); i++) 
            {
                System.out.println("process name : " + done.get(i).process_name);
                System.out.println("process start time : " + done.get(i).start_time);
                System.out.println("process end time : " + done.get(i).end_time);
                System.out.println("---------------------------------------------------------------------------");
            }
            System.out.println("\nWaiting and Turn around for each process : \n");
            float averageWaitingTime = 0;
            float averageTurnAroundTime = 0;
            for (int i = 0; i < processes2.size(); i++) 
            {
                System.out.println("process Name : " + processes2.get(i).process_name);
                System.out.println("Turn around Time : "
                        + processes2.get(i).turnaround_time);
                averageTurnAroundTime += processes2.get(i).turnaround_time;
                System.out.println("Waiting Time : "
                        + processes2.get(i).turnaround_time);
                averageWaitingTime += processes2.get(i).turnaround_time;
                System.out.println("********************************************");
            }
            averageTurnAroundTime = averageTurnAroundTime / processes2.size();
            averageWaitingTime = averageWaitingTime / processes2.size();
            System.out.println("average turn around time : " + averageTurnAroundTime);
            System.out.println("average waiting time : " + averageWaitingTime);
        }
     
        public void shortestJobFirst() throws CloneNotSupportedException 
        {
                int min = getMinArrivalTime();
                Process p = new Process();
                for (int time = min; processes.size() != 0 ; time++)
                {
                    for (int i = 0; i < processes.size(); i++) 
                    {
                        if (processes.get(i).arrival_time == time) 
                        {
                            arrived2.add(processes.get(i));
                        }
                    }
                    if (arrived2.size() != 0) 
                    {
                        p = findMin(arrived2);
                    }
                    if (done.size() == 0)
                    {
                        p.start_time = min;
                        p.end_time = min+ p.burst_time;
                        p.waiting_time = 0;
                        p.turnaround_time = p.burst_time;
                        Process temp = (Process) p.clone();
                        done.add(temp);
                        arrived2.remove(p);
                        processes.remove(p);
                        IncreamentTime(p.end_time);
                        time=p.end_time-1;
                    }
                    else 
                    {
                        p.start_time = done.get(done.size() - 1).end_time;
                        p.end_time = p.start_time + p.burst_time;
                        p.waiting_time = p.start_time - p.arrival_time;
                        p.turnaround_time = p.end_time - p.arrival_time;
                        Process temp = (Process) p.clone();
                        done.add(temp);
                        arrived2.remove(p);
                        processes.remove(p);
                        IncreamentTime(p.end_time);
                        time=p.end_time-1;
                    }
                }
     
                for (int i = 0; i < done.size(); i++) 
                {
                    System.out.println("process name : " + done.get(i).process_name);
                    System.out.println("process start time : " + done.get(i).start_time);
                    System.out.println("process end time : " + done.get(i).end_time);
                    System.out.println("process waiting time : " + done.get(i).waiting_time);
                    System.out.println("process turnaround time : "	+ done.get(i).turnaround_time);
                    System.out.println("-----------------------");
                }
                float averageWaitingTime = 0;
                float averageturnAroundTime = 0;
                for (int i = 0; i < done.size(); i++) 
                {
                    averageWaitingTime += done.get(i).waiting_time;
                    averageturnAroundTime += done.get(i).turnaround_time;
                }
                System.out.println("Average waiting time is : " + averageWaitingTime/ done.size());
                System.out.println("Average turn around time is : " + averageturnAroundTime / done.size());
                done.clear();
        }
    }