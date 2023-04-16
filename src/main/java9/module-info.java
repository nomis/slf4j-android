module uk.uuid.slf4j.android {
	requires org.slf4j;
	provides org.slf4j.spi.SLF4JServiceProvider with uk.uuid.slf4j.android.ServiceProvider;
}
