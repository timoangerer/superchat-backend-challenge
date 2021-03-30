CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- public.channel_types definition

-- Drop table

-- DROP TABLE public.channel_types;

CREATE TABLE public.channel_types (
	id serial NOT NULL,
	type_name varchar(30) NOT NULL,
	CONSTRAINT channel_types_pkey PRIMARY KEY (id)
);


-- public.contacts definition

-- Drop table

-- DROP TABLE public.contacts;

CREATE TABLE public.contacts (
	id uuid NOT NULL DEFAULT uuid_generate_v1(),
	name varchar NULL,
	email varchar(254) NULL,
	CONSTRAINT contacts_email_key UNIQUE (email),
	CONSTRAINT contacts_pkey PRIMARY KEY (id)
);


-- public.channels definition

-- Drop table

-- DROP TABLE public.channels;

CREATE TABLE public.channels (
	id uuid NOT NULL DEFAULT uuid_generate_v1(),
	contact_id uuid NULL,
	channel_type_id int4 NOT NULL,
	CONSTRAINT channels_pkey PRIMARY KEY (id),
	CONSTRAINT channels_contactid_fkey FOREIGN KEY (contact_id) REFERENCES contacts(id),
	CONSTRAINT channels_fk FOREIGN KEY (channel_type_id) REFERENCES channel_types(id)
);


-- public.messages definition

-- Drop table

-- DROP TABLE public.messages;

CREATE TABLE public.messages (
	id uuid NOT NULL DEFAULT uuid_generate_v1(),
	contact_id uuid NULL,
	channel_id uuid NULL,
	sent_by_contact bool NULL,
	sent_at timestamp NULL,
	"text" varchar NULL,
	CONSTRAINT messages_pkey PRIMARY KEY (id),
	CONSTRAINT messages_channel_id_fkey FOREIGN KEY (channel_id) REFERENCES channels(id),
	CONSTRAINT messages_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES contacts(id)
);


-- public.whatsapp_channels definition

-- Drop table

-- DROP TABLE public.whatsapp_channels;

CREATE TABLE public.whatsapp_channels (
	id uuid NOT NULL DEFAULT uuid_generate_v1(),
	channel_id uuid NULL,
	wa_id varchar NOT NULL,
	CONSTRAINT whatsapp_channels_pkey PRIMARY KEY (id),
	CONSTRAINT whatsapp_channels_wa_id_key UNIQUE (wa_id),
	CONSTRAINT whatsapp_channels_channel_id_fkey FOREIGN KEY (channel_id) REFERENCES channels(id)
);

insert into channel_types (id, type_name) values (1, 'whatsapp');
