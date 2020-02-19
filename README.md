# CPU-Scheduler-Simulator

CPU Schedular Simulator: is a Project for Simulating different CPU Techniques to handle Processes in Ready Queue. Techniques Simulated (Shortest Job First, Shortest Remaining Time First, Priority Scheduling and AG)

    Summary Describe each Technique.

    1. SJR(Shortest Job First) : Process which have the shortest burst time are scheduled first.If two processes have the same bust time then FCFS is used to break the tie. It is a non-preemptive scheduling algorithm.

    2. SRTF(Shortest Remaining Time First) : It is preemptive of SJF algorithm in which we give priorty to the process having shortest burst time remaining.

    3. Priority Scheduling : In this scheduling, processes are scheduled according to their priorities, i.e., highest priority process is scheduled first. If priorities of two processes match, then schedule according to arrival time. Here starvation of process is possible.

    4. AG : in this scheduling each Process has Arrival Time, Burst Time and Priority , Quantum and AG Factor. each process excution Time does not exceed it's Quantum. AG Factor = Arrival Time + Burst Time + Priority. It is Non-preemptive Technique. CPU Takes the first Process in the ready queue, after half of Execution Time of the Current Process till end of it. CPU checks the ready if there ia any better Process "better Process means a Process with lower AG Factor". other wise continue excution when finishing, CPU takes first Process in the Ready Queue.
