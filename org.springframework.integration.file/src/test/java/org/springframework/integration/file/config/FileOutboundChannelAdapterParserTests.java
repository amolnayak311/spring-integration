/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.file.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.endpoint.OutboundChannelAdapter;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileWritingMessageConsumer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Fisher
 * @author Marius Bogoevici
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class FileOutboundChannelAdapterParserTests {

	@Autowired
	@Qualifier("simpleAdapter")
	OutboundChannelAdapter simpleAdapter;

	@Autowired
	@Qualifier("adapterWithCustomNameGenerator")
	OutboundChannelAdapter adapterWithCustomNameGenerator;


	@Test
	public void simpleAdapter() {
		DirectFieldAccessor adapterAccessor = new DirectFieldAccessor(simpleAdapter);
		FileWritingMessageConsumer consumer = (FileWritingMessageConsumer)
				adapterAccessor.getPropertyValue("consumer");
		DirectFieldAccessor consumerAccessor = new DirectFieldAccessor(consumer);
		assertEquals(System.getProperty("java.io.tmpdir"),
				((File) consumerAccessor.getPropertyValue("parentDirectory")).getAbsolutePath());
		assertTrue(consumerAccessor.getPropertyValue("fileNameGenerator") instanceof DefaultFileNameGenerator);
	}

	@Test
	public void adapterWithCustomFileNameGenerator() {
		DirectFieldAccessor adapterAccessor = new DirectFieldAccessor(adapterWithCustomNameGenerator);
		FileWritingMessageConsumer consumer = (FileWritingMessageConsumer)
				adapterAccessor.getPropertyValue("consumer");
		DirectFieldAccessor consumerAccessor = new DirectFieldAccessor(consumer);
		assertEquals(System.getProperty("java.io.tmpdir"),
				((File) consumerAccessor.getPropertyValue("parentDirectory")).getAbsolutePath());
		assertTrue(consumerAccessor.getPropertyValue("fileNameGenerator") instanceof CustomFileNameGenerator);
	}

}
