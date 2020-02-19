package cpu.scheduling;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mbakr
 */
public class SRTF_Scheduling {

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

    private Process findMin(ArrayList<Process> processes4)
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

    public void SRTF() throws CloneNotSupportedException 
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the context switch : ");
        int contextSwitch = in.nextInt();
        int min = getMinArrivalTime();
        ArrayList<Process> arrived = new ArrayList<Process>();
        processes2.clear();
        Process p = new Process();
        boolean flag = false;
        boolean check = false;
        for (int i = min; processes2.size() != processes.size(); i++) 
        {
            for (int j = 0; j < processes.size(); j++) 
            {
                if (processes.get(j).arrival_time == i) 
                {
                    arrived.add(processes.get(j));
                    flag = true;
                }
            }
            if (flag) 
            {
                if (i != min && findMin(arrived) != p)
                {
                    for (int m = i + 1; m <= i + contextSwitch; m++) 
                    {
                        for (int z = 0; z < processes.size(); z++) 
                        {
                            if (processes.get(z).arrival_time == m) 
                            {
                                arrived.add(processes.get(z));
                            }
                        }
                    }
                }
                Process temp = (Process) p.clone();
                if (i != min && check == false && findMin(arrived) != p) 
                {
                    temp.end_time = i;
                    if (done.size() == 0) 
                    {
                        temp.start_time = min;
                    }
                    done.add(temp);
                }
                check = false;
                if (findMin(arrived) != p) 
                {
                    p = findMin(arrived);
                    if (done.size() != 0) 
                    {
                        p.start_time = i + contextSwitch ;
                    }
                    if (i != min) 
                    {
                        i += contextSwitch;
                    }
                }
                flag = false;
            }
            p.burst_time--;
            while (true)
            {
                if (p.burst_time == 0) 
                {
                    i++;
                    p.end_time = i;
                    p.turnaround_time = p.end_time - getArrivalTime(p);
                    p.waiting_time = p.turnaround_time - getBurstTime(p);
                    Process temp3 = (Process) p.clone();
                    processes2.add(temp3);
                    check = true;
                    arrived.remove(p);
                    for (int m = i; m <= i + contextSwitch; m++) 
                    {
                        for (int z = 0; z < processes.size(); z++) 
                        {
                            if (processes.get(z).arrival_time == m) 
                            {
                                arrived.add(processes.get(z));
                            }
                        }
                    }
                    Process temp2 = (Process) p.clone();
                    if (done.size() == 0) 
                    {
                        temp2.start_time = min;
                    }
                    done.add(temp2);
                    if (arrived.size() != 0) 
                    {
                        p = findMin(arrived);
                        p.start_time = i + contextSwitch;
                        p.burst_time--;
                    } 
                    else 
                    {
                        break;
                    }
                    i += contextSwitch;
                    if (p.burst_time != 0)
                    {
                        break;
                    }
                } 
                else 
                {
                    break;
                }
            }
        }
    }
}
