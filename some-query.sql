
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SET TRANSACTION READ WRITE;
SET CONSTRAINTS ALL DEFERRED;

select id into soeids from (
	select id from (
		select t2.id, t2.seat_id, t2.modified, row_number()
			over (partition by t2.seat_id order by t2.modified) as row_id
		from 
			(select s.* from soe s 
				join (select seat_id, event_id from soe group by seat_id, event_id, sector_id having seat_id is not null and count(*) > 1) t1
				on t1.seat_id = s.seat_id 	
				where  t1.event_id = s.event_id)  t2
			) t3	
		where t3.row_id <> 1
) t4;

delete from ticket_preorder where soe_invoice_item_id in (
	select id from soe_invoice_item where soe_id in (select * from soeids));

delete from ticket_form where ticket_id in (
	select id from ticket where soe_invoice_item_id in (
	select id from soe_invoice_item where soe_id in (select * from soeids)));

delete from ticket_history where ticket_id in (
	select id from ticket where soe_invoice_item_id in (
	select id from soe_invoice_item where soe_id in (select * from soeids)));

delete from ticket_invoice_item where ticket_id in (
	select id from ticket where soe_invoice_item_id in (
	select id from soe_invoice_item where soe_id in (select * from soeids)));

delete from ticket where soe_invoice_item_id in (
	select id from soe_invoice_item where soe_id in (select * from soeids));

delete from soe_invoice_item where soe_id in (select * from soeids);

delete from soe where id in (select * from soeids);

drop table soeids;

COMMIT;
