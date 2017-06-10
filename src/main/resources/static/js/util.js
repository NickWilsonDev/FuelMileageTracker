
function allRecordsForOneUnitAjax() {
        $.ajax({
            url : 'ajaxAllRecordsByUnitNumber.html',
            data: { unitNumber: document.getElementById('unitNumber').value
            },
            success : function(data) {
                $('#result').html(data);
            }
        });
    }

	function betweenTwoDatesAjax() {
		$.ajax({
			url : 'ajaxAllRecordsBetweenTwoDates',
			data: { startDate: document.getElementById('startDate').value, 
					endDate: document.getElementById('endDate').value
			},
			success : function(data) {
				// make object {
				//	unique states : [miles, fuel]
				// }
				var statesSet = new Set();
				var obj = {};
				var recordArray = JSON.parse(data);
				for( var key in recordArray ) {
				    statesSet.add(recordArray[key].state);
				    var tempState = recordArray[key].state;
				    if (obj.hasOwnProperty(tempState)) {
				    	obj[tempState][1] += recordArray[key].miles; 
				    	obj[tempState][0] += recordArray[key].fuel;
				    } else {
				    	// otherwise create new entry and set up mile and fuel array
				    	var fuelMileArray = [recordArray[key].fuel, recordArray[key].miles];
				    	obj[tempState] = fuelMileArray;
				    }
				}
				$('#basicReport').html(data);
				generateTable(obj, statesSet);
				drawMileagePieChart(d3, data);
				drawFuelPieChart(d3, data);
				drawMultiLineChart(d3, data);
			}
		})
	}
	
		function generateTable(obj, statesSet) {
			//Create a HTML Table element.
		    var table = document.createElement("TABLE");
		    table.border = "1";
		    table.className = "table table-hover";
		    
		    //Add the header row.
		    var row = table.insertRow(-1);
	        var headerCell = document.createElement("TH");
		    headerCell.innerHTML = "State";
		    row.appendChild(headerCell);
		    var headerCell = document.createElement("TH");
		    headerCell.innerHTML = "Miles";
		    row.appendChild(headerCell);
		    var headerCell = document.createElement("TH");
		    headerCell.innerHTML = "Fuel";
		    row.appendChild(headerCell);
		    
		    // add data rows
		    var dataCell;
		    statesSet.forEach(function(value) {
  				row = table.insertRow(-1);
  				dataCell = document.createElement("TH");
  				dataCell.innerHTML = value;
  				row.appendChild(dataCell);
  				dataCell = document.createElement("TH");
  				dataCell.innerHTML = obj[value][1];
  				row.appendChild(dataCell);
  				dataCell = document.createElement("TH");
  				dataCell.innerHTML = obj[value][0];
  				row.appendChild(dataCell);
			});
		    
		    var dvTable = document.getElementById("basicReport");
		    dvTable.innerHTML = "";
		    dvTable.appendChild(table);
		}
		
		function drawMileagePieChart(d3, jsonString) {
	        var dataset = JSON.parse(jsonString); 
	        
	        var datasetGroupByUnitNumber = d3.nest()
	        			.key(function(d) { return d.unitNumber; })
	        			.rollup(function(v) { return {
	        				miles : d3.sum(v, function(d) { return d.miles; }),
	        				fuel  : d3.sum(v, function(d) { return d.fuel; })
	        				}; 
	        			})
	        			.entries(dataset);
	        
	        if ($("#pieChartAllMiles > svg").length == 1) {
	        	d3.select("#pieChartAllMiles > svg").remove();
	        }
        	var width = 250;
        	var height = 250;
        	var radius = Math.min(width, height) / 2;
        	var color = d3.scaleOrdinal(d3.schemeCategory20b);
	        	
        	var svg = d3.select('#pieChartAllMiles')
        			.append('svg')
        			.attr('width', width)
        			.attr('height', height)
        			.append('g')
        			.attr('transform', 'translate(' + (width / 2) +
        					',' + (height / 2) + ')');
	        
        	var tooltip = d3.select("body")
        			.append("div")
        			.attr('class', 'tooltip')
        			.style("opacity", 0);
        		tooltip.append('div')
        			.attr('class', 'label');
	        
        	var arc = d3.arc()
    		.innerRadius(0)
    		.outerRadius(radius);
	        
        	var pie = d3.pie()
        		.value(function(d) { 
        			return d.value.miles;
        		})
        		.sort(null);
	        
        	var path = svg.selectAll('path')
        		.data(pie(datasetGroupByUnitNumber), function(d) { return d.data.key; })
        		.enter()
        		.append('path')
        		.attr('d', arc)
        		.attr('fill', function(d, i) {
        			return color(d.data.key);
        		})
        		.on("mouseover", function(d){
        			tooltip.select('.label').html("<center>" + d.data.key + "<br>" + d.data.value.miles + " miles" + "</center>");                // NEW
        			tooltip.transition()
                    	.duration(500)
        				.style('opacity', 1);})
                    
        		.on("mousemove", function(){return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
        		.on("mouseout", function(){return tooltip.style("opacity", 0);});
  
        	/* animate filling of slices */
        	path.transition()
            	.duration(function(d, i) {
            		return i * 600;
            	})
            	.attrTween('d', function(d) {
            		var i = d3.interpolate(d.startAngle+0.1, d.endAngle);
            		return function(t) {
            			d.endAngle = i(t);
            			return arc(d);
            		}
          		})
            /* end of animation of filling of slices */
          		.each(function(d) {
          			this._current = d; // store initial angles
          		});
		}
		
		function drawFuelPieChart(d3, jsonString) {
	        var dataset = JSON.parse(jsonString); 
	        
	        var datasetGroupByUnitNumber = d3.nest()
			.key(function(d) { return d.unitNumber; })
			.rollup(function(v) { return {
				miles : d3.sum(v, function(d) { return d.miles; }),
				fuel  : d3.sum(v, function(d) { return d.fuel; })
				}; 
			})
			.entries(dataset);
	        
	        if ($("#pieChartAllFuel > svg").length == 1) {
	        	d3.select("#pieChartAllFuel > svg").remove();
	        }
        	var width = 250;
        	var height = 250;
        	var radius = Math.min(width, height) / 2;
        	var color = d3.scaleOrdinal(d3.schemeCategory20b);
	        	
        	var svg = d3.select('#pieChartAllFuel')
        		.append('svg')
        		.attr('width', width)
        		.attr('height', height)
        		.append('g')
        		.attr('transform', 'translate(' + (width / 2) +
        			',' + (height / 2) + ')');
	        
        	var tooltip = d3.select("body")
				.append("div")
				.attr('class', 'tooltip')
				.style("opacity", 0);
        	tooltip.append('div')
				.attr('class', 'label');
	        
        	var arc = d3.arc()
    		.innerRadius(0)
    		.outerRadius(radius);
	        
        	var pie = d3.pie()
        		.value(function(d) { 
        			return d.value.fuel;
        		})
        		.sort(null);
	        
        	var path = svg.selectAll('path')
        		.data(pie(datasetGroupByUnitNumber), function(d) { return d.data.key; })
        		.enter()
        		.append('path')
        		.attr('d', arc)
        		.attr('fill', function(d, i) {
        			return color(d.data.key);
        		})
        		.on("mouseover", function(d){
        			tooltip.select('.label').html("<center>" + d.data.key + "<br>" + d.data.value.fuel + " gallons" + "</center>");                // NEW
        			tooltip.transition()
                    	.duration(500)
        				.style('opacity', 1);})
        			.on("mousemove", function(){return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
        			.on("mouseout", function(){return tooltip.style("opacity", 0);});
        	
        	/* animate filling of slices */
        	path.transition()
            	.duration(function(d, i) {
            		return i * 600;
            	})
            	.attrTween('d', function(d) {
            		var i = d3.interpolate(d.startAngle+0.1, d.endAngle);
            		return function(t) {
            			d.endAngle = i(t);
            			return arc(d);
            		}
          		})
            /* end of animation of filling of slices */
          		.each(function(d) {
          			this._current = d; // store initial angles
          		});
		}
		
		function drawMultiLineChart(d3, jsonString) {
			console.log(JSON.stringify(jsonString));
			var dataset = JSON.parse(jsonString); 
	        console.log("datset after JSON parse -----------");
			console.log("dataset " + dataset);
			// group by unit number
	        var datasetGroupByUnitNumber = d3.nest()
	        			.key(function(d) { return d.unitNumber; })
	        			.entries(dataset);
	        console.log("multilinechart data " + JSON.stringify(datasetGroupByUnitNumber));
	        
	        // might be best to split records into separate arrays of records based on unit number
	        if ($("#multiLineChartOverTime > svg").length == 1) {
	        	d3.select("#pieChartAllFuel > svg").remove();
	        }
	        // temp values will need changing
	     // Set the dimensions of the canvas / graph
	        //var margin = {top: 30, right: 20, bottom: 30, left: 50},
	        //    width = 600 - margin.left - margin.right,
	        //    height = 270 - margin.top - margin.bottom;
	        
	        //var vis = d3.select("#multiLineChartOverTime"),
	        //WIDTH = 1000,
	        //HEIGHT = 500,
	        //MARGINS = {
	        //    top: 20,
	        //    right: 20,
	        //    bottom: 20,
	        //    left: 50
	        //},
	        
	        var color = d3.scaleOrdinal(d3.schemeCategory20b);
            var vis = d3.select("#multiLineChartOverTime"),
                WIDTH = 1000,
                HEIGHT = 500,
                MARGINS = {
                    top: 50,
                    right: 20,
                    bottom: 50,
                    left: 50
                },
                lSpace = WIDTH/datasetGroupByUnitNumber.length;
            console.log("d.values.id" + function(d) {return d.id;});
        	console.log("d.values.id" + d.values.id);
                xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(data, function(d) {
                    
                	return d.values.id;
                }), d3.max(data, function(d) {
                    return d.values.id;
                })]),
                yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(data, function(d) {
                    return d.miles;
                }), d3.max(data, function(d) {
                    return d.miles;
                })]),
                xAxis = d3.svg.axis()
                .scale(xScale),
                yAxis = d3.svg.axis()
                .scale(yScale)
                .orient("left");
            
            vis.append("svg:g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                .call(xAxis);
            vis.append("svg:g")
                .attr("class", "y axis")
                .attr("transform", "translate(" + (MARGINS.left) + ",0)")
                .call(yAxis);
                
            var lineGen = d3.svg.line()
                .x(function(d) {
                    return xScale(d.year);
                })
                .y(function(d) {
                    return yScale(d.miles);
                })
                .interpolate("basis");
            datasetGroupByUnitNumber.forEach(function(d,i) {
                vis.append('svg:path')
                .attr('d', lineGen(d.values))
                .attr('stroke', function(d,j) { 
                        return "hsl(" + Math.random() * 360 + ",100%,50%)";
                })
                .attr('stroke-width', 2)
                .attr('id', 'line_'+d.key)
                .attr('fill', 'none');
            });
		}
