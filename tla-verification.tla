--------------------------- MODULE step5_prodcons ---------------------------

EXTENDS TLC, Integers, Sequences
CONSTANTS MaxQueueSize

(*--algorithm message_queue
variable pathqueue = <<>>;
variable statsqueue = <<>>;
define
  BoundedPathQueue == Len(pathqueue) <= MaxQueueSize 
  BoundedStatsQueue == Len(statsqueue) <= MaxQueueSize
end define;

process pathproducer \in { "pathproducer" } 
variable item = "";
begin Produce:
  while TRUE do
    produce:
        item := "item";
    put: 
        await Len(pathqueue) < MaxQueueSize;
        pathqueue := Append(pathqueue, item);
  end while;
end process;

process pathconsumer \in { "pathconsumer1", "pathconsumer2" }
variable item = "none";
begin Consume:
  while TRUE do
    take: 
        await pathqueue /= <<>>;
        item := Head(pathqueue);
        pathqueue := Tail(pathqueue);
    consume:
        await Len(statsqueue) < MaxQueueSize;
        statsqueue := Append(statsqueue, item);
  end while;
end process;

process statsconsumer \in { "statsconsumer" }
variable item = "none";
begin Consume:
  while TRUE do
    take: 
        await statsqueue /= <<>>;
        item := Head(statsqueue);
        statsqueue := Tail(statsqueue);
    consume:
        print item;
  end while;
end process;

end algorithm;*)

\* BEGIN TRANSLATION (chksum(pcal) = "26c3172" /\ chksum(tla) = "e7cb84b")
\* Process variable item of process producer at line 13 col 10 changed to item_
VARIABLES queue, pc

(* define statement *)
BoundedQueue == Len(queue) <= MaxQueueSize

VARIABLES item_, item

vars == << queue, pc, item_, item >>

ProcSet == ({ "prod1", "prod2" }) \cup ({ "cons1", "cons2" })

Init == (* Global variables *)
        /\ queue = <<>>
        (* Process producer *)
        /\ item_ = [self \in { "prod1", "prod2" } |-> ""]
        (* Process consumer *)
        /\ item = [self \in { "cons1", "cons2" } |-> "none"]
        /\ pc = [self \in ProcSet |-> CASE self \in { "prod1", "prod2" } -> "Produce"
                                        [] self \in { "cons1", "cons2" } -> "Consume"]

Produce(self) == /\ pc[self] = "Produce"
                 /\ pc' = [pc EXCEPT ![self] = "produce"]
                 /\ UNCHANGED << queue, item_, item >>

produce(self) == /\ pc[self] = "produce"
                 /\ item_' = [item_ EXCEPT ![self] = "item"]
                 /\ pc' = [pc EXCEPT ![self] = "put"]
                 /\ UNCHANGED << queue, item >>

put(self) == /\ pc[self] = "put"
             /\ Len(queue) < MaxQueueSize
             /\ queue' = Append(queue, item_[self])
             /\ pc' = [pc EXCEPT ![self] = "Produce"]
             /\ UNCHANGED << item_, item >>

producer(self) == Produce(self) \/ produce(self) \/ put(self)

Consume(self) == /\ pc[self] = "Consume"
                 /\ pc' = [pc EXCEPT ![self] = "take"]
                 /\ UNCHANGED << queue, item_, item >>

take(self) == /\ pc[self] = "take"
              /\ queue /= <<>>
              /\ item' = [item EXCEPT ![self] = Head(queue)]
              /\ queue' = Tail(queue)
              /\ pc' = [pc EXCEPT ![self] = "consume"]
              /\ item_' = item_

consume(self) == /\ pc[self] = "consume"
                 /\ PrintT(item[self])
                 /\ pc' = [pc EXCEPT ![self] = "Consume"]
                 /\ UNCHANGED << queue, item_, item >>

consumer(self) == Consume(self) \/ take(self) \/ consume(self)

Next == (\E self \in { "prod1", "prod2" }: producer(self))
           \/ (\E self \in { "cons1", "cons2" }: consumer(self))

Spec == Init /\ [][Next]_vars

\* END TRANSLATION 

=============================================================================
\* Modification History
\* Last modified Tue Apr 11 17:58:42 CEST 2023 by alessandro
\* Last modified Sun Mar 28 15:40:26 CEST 2021 by aricci
\* Created Sun Mar 28 08:34:06 CEST 2021 by aricci

=============================================================================
\* Modification History
\* Created Sun Mar 28 15:32:19 CEST 2021 by aricci
