<h1> Spring-JDBC-Demo </h1>
<p>This demo to show the advantages of JDBCTemplate over JDBC. For this demo I use Postgres database.</p>
<p>The table needed for this demo is give below </p>

<pre>
	CREATE TABLE products
	(
	  id serial NOT NULL,
	  description character varying(50),
	  price numeric(15,2),
	  CONSTRAINT products_pkey PRIMARY KEY (id)
	);
</pre> 
